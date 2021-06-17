<!-- 文件目录组件 -->
<template>
  <div>
    <el-drawer
      title="文件目录"
      :visible.sync="drawerVisible"
      :with-header="false"
      direction="ltr"
      :modal="false"
      :size="drawerSize"
    >
      <div class="fileList">
        <p id="title">文件目录</p>
        <!-- 文件名搜索框 -->
        <el-input placeholder="输入文件名进行查询" v-model="filterText">
        </el-input>
        <!-- 文件目录 -->
        <el-button type="text" @click="initFiles" id="clearButton"
          >清空所有文件</el-button
        >
        <el-tree
          class="filter-tree"
          :data="dataList"
          default-expand-all
          :filter-node-method="filterNode"
          :props="defaultProps"
          ref="tree"
          highlight-current
          check-on-click-node
          @node-click="clickNode"
        >
          <span class="custom-tree-node" slot-scope="{ node, data }">
            <span><i :class="data.icon"></i>{{ node.label }}</span>
            <span>
              <el-button
                type="text"
                size="mini"
                @click.stop="append(data)"
                v-show="data.judgeFolder"
                >添加</el-button
              >
              <el-button
                type="text"
                size="mini"
                @click.stop="remove(data)"
                v-show="!data.rootFlag"
                >删除</el-button
              >
              <el-button
                type="text"
                size="mini"
                @click.stop="rename(node, data)"
                v-show="!data.rootFlag"
                >重命名</el-button
              >
            </span>
          </span>
        </el-tree>
        <file-form @onSubmit="loadFiles" ref="edit"></file-form>
      </div>
    </el-drawer>
  </div>
</template>

<script>
import FileForm from './FileForm.vue'
export default {
  data() {
    return {
      filterText: '',
      dataList: [],
      menuVisible: false,
      defaultProps: {
        children: 'children',
        label: 'label'
      },
      drawerVisible: false,
      drawerSize: '400'
    }
  },
  components: { FileForm },

  // 会在页面加载时候执行一次，目的是从后端拿到文件目录
  mounted: function() {
    this.loadFiles()
  },

  watch: {
    filterText(val) {
      this.$refs.tree.filter(val)
    }
  },

  methods: {
    filterNode(value, dataList) {
      if (!value) return true
      return dataList.label.indexOf(value) !== -1
    },

    // 从后端加载文件目录
    loadFiles() {
      var _this = this
      _this.$axios.get('/file_list/list_all').then(resp => {
        if (resp.data.code === 200) {
          let root = resp.data.data
          root.rootFlag = true
          _this.changeIcon(root)
          // console.log(root)
          _this.dataList = [root]
        }
      })
    },

    // 根据 judgeFolder 属性添加 icon 属性，然后显示图标
    changeIcon(node) {
      if (node.judgeFolder === true) {
        node.icon = 'el-icon-folder'
      } else {
        node.icon = 'el-icon-document'
      }
      // console.log(node.children.length)
      // console.log(node.children)
      if (node.children.length !== 0) {
        var _this = this
        for (let i = 0; i < node.children.length; i++) {
          _this.changeIcon(node.children[i])
        }
      }
    },

    // 添加一个子目录节点
    append(data) {
      var _this = this
      _this.$refs.edit.dialogFormVisible = true
      _this.$refs.edit.renameFlag = false
      _this.$refs.edit.parentChildren = data.children
      _this.$refs.edit.form = {
        id: '',
        label: '',
        judgeFolder: false,
        parentId: data.id,
        fileNodeLevel: data.fileNodeLevel + 1
      }
    },

    // 删除一个文件或者目录
    remove(data) {
      var _this = this
      _this
        .$confirm('确定删除？', '提示', {
          confirmButtonText: '确定',
          cancelButtontext: '取消',
          type: 'warning'
        })
        .then(() => {
          _this.$axios
            .post('/file_list/delete_file', {
              id: data.id,
              children: data.children
            })
            .then(resp => {
              if (resp.data.code === 200) {
                _this.loadFiles()
              }
            })
        })
        .then(() =>
          _this.$message({
            type: 'success',
            message: '删除成功'
          })
        )
        .catch(() =>
          _this.$message({
            type: 'info',
            message: '取消删除'
          })
        )
    },

    // 重命名一个文件或者文件夹
    rename(node, data) {
      var _this = this
      _this.$refs.edit.dialogFormVisible = true
      _this.$refs.edit.renameFlag = true
      _this.$refs.edit.parentChildren = node.parent.data.children
      _this.$refs.edit.form = {
        id: data.id,
        label: data.label,
        judgeFolder: data.judgeFolder,
        parentId: data.parentId,
        fileNodeLevel: data.fileNodeLevel
      }
    },

    // 清空所有的目录
    initFiles() {
      var _this = this
      _this
        .$confirm('此操作将删除所有的目录和文件，是否继续？', '提示', {
          confirmButtonText: '确定',
          cancelButtontext: '取消',
          type: 'warning'
        })
        .then(() =>
          _this.$axios.get('/file_list/init_list').then(resp => {
            if (resp.data.code === 200) {
              _this.loadFiles()
            }
          })
        )
        .then(() => {
          _this.$message({
            type: 'success',
            message: '删除成功!'
          })
        })
        .catch(() => {
          _this.$message({
            type: 'info',
            message: '已经取消删除'
          })
        })
    },
    // 点击节点触发事件
    clickNode(data, node, obj) {
      var _this = this
      _this.$emit('clickNode', data)
    }
  }
}
</script>

<style scoped>
.fileList {
  width: 400px;
}
.el-tree {
  border-top-style: solid;
}
.custom-tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  padding-right: 8px;
}
#title {
  font-size: 35px;
  margin-top: 60px;
}
#clearButton {
  margin-top: 20px;
}
</style>
