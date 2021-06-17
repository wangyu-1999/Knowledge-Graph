<!-- 子图的列表 -->
<template>
  <div>
    <el-drawer
      title="子图列表"
      :visible.sync="drawerVisible"
      :with-header="false"
      direction="ltr"
      :modal="false"
      :size="drawerSize"
    >
      <div id="tagList">
        <div id="title">子图存取</div>
        <el-tag
          :key="tag"
          v-for="tag in tagList"
          closable
          :disable-transitions="false"
          @close="handleClose(tag)"
          @click="clickTag(tag)"
        >
          {{ tag }}
        </el-tag>
        <el-input
          class="input-new-tag"
          v-if="inputVisible"
          v-model="inputValue"
          ref="saveTagInput"
          size="small"
          @keyup.enter.native="handleInputConfirm"
          @blur="handleInputConfirm"
        >
        </el-input>
        <el-button v-else class="button-new-tag" size="small" @click="showInput"
          >添加子图</el-button
        >
      </div>
    </el-drawer>
  </div>
</template>

<script>
export default {
  data() {
    return {
      drawerVisible: false,
      drawerSize: '400',
      fileId: null,
      tagList: [],
      inputVisible: false,
      inputValue: null
    }
  },
  mounted: function() {
    if (this.fileId !== null && this.fileId !== '') {
      this.init()
    }
  },
  watch: {
    fileId: function() {
      this.init()
    }
  },
  methods: {
    // 初始化子图列表
    init() {
      var _this = this
      _this.$axios
        .post('/subgraph/list_all', {
          id: _this.fileId
        })
        .then(resp => {
          if (resp.data.code === 200) {
            let resList = []
            for (let item of resp.data.data) {
              resList.push(item.name)
            }
            _this.tagList = resList
          }
        })
    },
    // 删除子图
    handleClose(tag) {
      var _this = this
      _this.$axios
        .post('/subgraph/delete', {
          name: tag,
          fileNode: {
            id: _this.fileId
          }
        })
        .then(resp => {
          if (resp.data.code === 200) {
            _this.init()
          }
        })
    },
    // 出现文本框
    showInput() {
      this.inputVisible = true
      this.$nextTick(_ => {
        this.$refs.saveTagInput.$refs.input.focus()
      })
    },
    // 添加子图
    handleInputConfirm() {
      var _this = this
      let inputValue = _this.inputValue
      for (let tag of _this.tagList) {
        if (tag === inputValue) {
          _this.inputVisible = false
          _this.inputValue = ''
          _this.$message({
            message: '不能有重名子图',
            type: 'warning'
          })
          return
        }
      }
      if (inputValue) {
        _this.$emit('addSubGraph', inputValue)
      }
      _this.inputVisible = false
      _this.inputValue = ''
    },
    // 加载子图
    clickTag(tag) {
      this.$emit('checkSubGraph', tag)
    }
  }
}
</script>

<style scoped>
#tagList {
  width: 400px;
  position: relative;
  top: 100px;
}
#title {
  font-size: 35px;
  margin-bottom: 50px;
}
.el-tag + .el-tag {
  margin-left: 10px;
}
.el-tag {
  cursor: pointer;
}
.button-new-tag {
  margin-left: 10px;
  height: 32px;
  line-height: 30px;
  padding-top: 0;
  padding-bottom: 0;
}
.input-new-tag {
  width: 90px;
  margin-left: 10px;
  vertical-align: bottom;
}
</style>
