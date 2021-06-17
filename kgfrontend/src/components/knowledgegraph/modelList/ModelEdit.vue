<!--模板目录界面-->
<template>
  <div>
    <div class="model">
      <p class="title">节点模板</p>
      <el-button type="primary" size="mini" @click="addModel('node')"
        >添加图元</el-button
      >
      <el-container>
        <node-model
          ref="nodeModel"
          @changed="init()"
          @fresh="fresh"
          :fileId="fileId"
        ></node-model>
      </el-container>
    </div>

    <div class="model">
      <p class="title">关系模板</p>
      <el-button type="primary" size="mini" @click="addModel('line')"
        >添加图元</el-button
      >
      <el-container>
        <line-model
          ref="lineModel"
          @changed="init()"
          @chosenLink="chosenLink"
        ></line-model>
      </el-container>
    </div>

    <el-dialog :visible.sync="dialogFormVisible" @close="clear" append-to-body>
      <el-form>
        <el-form-item label="新建模板">
          <el-input
            v-model="form.name"
            :placeholder="inputWord"
            maxlength="15"
            show-word-limit
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">确定</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import LineModel from './LineModel.vue'
import NodeModel from './NodeModel.vue'
export default {
  data() {
    return {
      dialogFormVisible: false,
      inputWord: '模板名称',
      form: {
        id: '',
        name: '',
        judgeNode: '',
        judgeAttribute: '',
        img: '',
        color: '',
        modelFileNode: {
          id: this.parentId
        }
      }
    }
  },
  props: {
    parentId: Number,
    fileId: Number
  },
  components: { NodeModel, LineModel },
  methods: {
    // 提交表单
    onSubmit() {
      var _this = this
      if (_this.form.name.trim() === '') {
        _this.$message({
          type: 'warning',
          message: '不能使用空或者空格命名'
        })
        _this.init()
        return
      }
      _this.$axios
        .post('/model/add_model', {
          name: _this.form.name,
          judgeNode: _this.form.judgeNode,
          judgeAttribute: _this.form.judgeAttribute,
          modelFileNode: {
            id: _this.parentId
          }
        })
        .then(resp => {
          if (resp.data.code === 200) {
            _this.init()
            _this.dialogFormVisible = false
          }
        })
    },
    // 关闭时清理数据
    clear() {
      var _this = this
      _this.form = {
        id: '',
        name: '',
        judgeNode: '',
        judgeAttribute: '',
        img: '',
        color: ''
      }
      _this.inputWord = '模板名称'
      _this.dialogFormVisible = false
    },
    // 添加模型
    addModel(type) {
      var _this = this
      _this.form.judgeAttribute = false
      if (type === 'node') {
        _this.form.judgeNode = true
        _this.inputWord = '节点模板名称'
      } else {
        _this.form.judgeNode = false
        _this.inputWord = '关系模板名称'
      }
      _this.dialogFormVisible = true
    },
    // 初始化
    init() {
      var _this = this
      _this.$refs.nodeModel.init(_this.parentId)
      _this.$refs.lineModel.init(_this.parentId)
    },
    // 向父组件发送信号
    fresh(val) {
      this.$emit('fresh', val)
    },
    chosenLink(val) {
      var _this = this
      _this.$emit('chosenLink', val)
    }
  },
  mounted: function() {
    this.init()
  }
}
</script>

<style scoped>
.model {
  margin-right: 20px;
  width: 100%;
  height: 100%;
  margin-top: 20px;
}
.title {
  font-size: 20px;
  margin-bottom: 5px;
}
.el-button {
  margin-bottom: 20px;
}
.el-container {
  width: 100%;
}
</style>
