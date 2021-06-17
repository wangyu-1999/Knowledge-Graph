<!--节点模板-->
<template>
  <div>
    <el-col :span="6" v-for="item in nodeModels" :key="item.id">
      <div class="card">
        <div class="cover">
          <img :src="item.img" class="nodeImage" />
        </div>
        <div>
          <i class="el-icon-setting icon" @click.stop="addAttribute(item)"></i>
          <i
            class="el-icon-circle-plus-outline icon"
            @click.stop="setName(item)"
          ></i>
          <i class="el-icon-delete icon" @click.stop="deleteModel(item)"></i>
        </div>
        <div class="name">
          <p>{{ item.name }}</p>
        </div>
      </div>
    </el-col>
    <el-dialog :visible="setDialogFormVisible" @close="clear" :modal="false">
      <attribute-form
        :choosenModel="choosenModel"
        ref="attributeForm"
        @changed="init"
      ></attribute-form>
    </el-dialog>
    <el-dialog :visible.sync="dialogFormVisible" @close="clear" :modal="false">
      <el-form>
        <el-form-item label="新建节点">
          <el-input v-model="form.name" placeholder="请输入节点名称"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="addToGraph">确定</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import AttributeForm from './AttributeForm.vue'
export default {
  props: {
    fileId: Number
  },
  data() {
    return {
      nodeModels: [],
      dialogFormVisible: false,
      setDialogFormVisible: false,
      choosenModel: {},
      form: {
        name: ''
      }
    }
  },
  components: { AttributeForm },
  methods: {
    // 根据模板文件的id初始化模板文件的内容
    init(file_id) {
      var _this = this
      _this.file = file_id
      _this.$axios
        .post('/model/list_all', {
          judgeNode: true,
          modelFileNode: {
            id: file_id
          }
        })
        .then(resp => {
          if (resp.data.code === 200) {
            _this.nodeModels = resp.data.data
          }
        })
    },
    // 关闭后初始化
    clear() {
      var _this = this
      _this.setDialogFormVisible = false
      _this.dialogFormVisible = false
      _this.choosenModel = ''
      _this.form = {
        name: ''
      }
    },
    // 添加属性
    addAttribute(item) {
      var _this = this
      _this.choosenModel = item
      _this.setDialogFormVisible = true
    },
    // 删除模型
    deleteModel(item) {
      var _this = this
      _this.$axios.post('/model/delete_model', item).then(resp => {
        if (resp.data.code === 200) {
          _this.$message.success('删除成功')
          _this.$emit('changed')
        }
      })
    },
    // 添加到图中
    setName(item) {
      var _this = this
      if (_this.fileId !== null) {
        _this.form = {
          noe4j_img: item.img,
          neo4j_file_id: _this.fileId,
          neo4j_model_name: item.name
        }
        _this.$axios.post('/model/init_attribute', item).then(resp => {
          if (resp.data.code === 200) {
            for (let attr of resp.data.data) {
              _this.form[attr.key] = ''
            }
          }
        })
        _this.dialogFormVisible = true
      }
    },
    // 添加到图中
    addToGraph() {
      var _this = this
      // console.log(_this.form)
      _this.$axios.post('/neo4j/add_node', _this.form).then(resp => {
        if (resp.data.code === 200) {
          // 向父组件发送信号
          _this.$emit('fresh', resp.data.data)
        }
        _this.clear()
      })
    }
  }
}
</script>

<style scoped>
.cover {
  width: 50px;
  height: 50px;
}
img.nodeImage {
  width: 100%;
  height: 100%;
  border-radius: 50%;
}
.name {
  font-size: 1px;
  word-wrap: break-word;
}
.card {
  height: 150px;
  width: 50px;
}
.icon {
  color: #204d7b;
}
.el-col {
  width: 100px;
}
</style>
