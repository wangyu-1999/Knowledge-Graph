<!--新建和修改文件和文件名的表单-->
<template>
  <div>
    <el-dialog
      :visible.sync="dialogFormVisible"
      @close="clear"
      width="500px"
      :append-to-body="true"
    >
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="模板名称">
          <el-input
            v-model="form.label"
            placeholder="请输入模板名称"
            maxlength="10"
            show-word-limit
          ></el-input>
        </el-form-item>
        <el-form-item label="文件夹">
          <el-switch
            v-model="form.judgeFolder"
            :disabled="renameFlag"
          ></el-switch>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">确定</el-button>
          <el-button @click="dialogFormVisible = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // 表单可视性，控制表单的显示和隐藏
      dialogFormVisible: false,
      // 控制文件或文件夹属性能否修改
      renameFlag: false,
      form: {
        id: '',
        label: '',
        parentId: '',
        judgeFolder: false,
        fileNodeLevel: ''
      },
      // 节点的子节点
      parentChildren: []
    }
  },
  components: {},
  methods: {
    // 关闭后初始化data中内容
    clear() {
      var _this = this
      _this.form = {
        id: '',
        label: '',
        parentId: '',
        judgeFolder: false,
        fileNodeLevel: ''
      }
      _this.parentChildren = []
      _this.renameFlag = false
    },
    // 提交时候调用的函数
    onSubmit() {
      var _this = this
      // 防止有空名字
      if (_this.form.label.trim() === '') {
        _this.$message({
          type: 'warning',
          message: '不能使用空或者空格命名'
        })
        return
      }
      // 新建文件时，防止发生重名
      for (let i = 0; i < _this.parentChildren.length; i++) {
        if (_this.parentChildren[i].label === _this.form.label) {
          _this.$message({
            type: 'warning',
            message: '不能与目录下已有的文件或模板重名'
          })
          return
        }
      }
      // 防止层数过多
      if (_this.form.fileNodeLevel > 4 && _this.form.judgeFolder === true) {
        _this.$message({
          type: 'warning',
          message: '模板最多只能包含4层'
        })
        return
      }
      _this.$axios
        .post('/model_list/add_file', {
          id: _this.form.id,
          label: _this.form.label,
          parentId: _this.form.parentId,
          judgeFolder: _this.form.judgeFolder,
          fileNodeLevel: _this.form.fileNodeLevel
        })
        .then(resp => {
          if (resp.data.code === 200) {
            _this.dialogFormVisible = false
            _this.$emit('onSubmit')
          }
        })
    }
  }
}
</script>

<style scoped>
.el-input {
  width: 300px;
}

.el-form {
  width: 450px;
}
</style>
