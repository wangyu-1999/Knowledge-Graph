<!--关系的模板-->
<template>
  <div>
    <el-col :span="6" v-for="item in lineModels" :key="item.id">
      <div class="card">
        <div class="cover">
          <img @click.stop="chooseLink(item)" :src="imgUrl" class="lineImage" />
        </div>
        <div>
          <i class="el-icon-setting icon" @click.stop="addAttribute(item)"></i>
          <i class="el-icon-delete icon" @click.stop="deleteModel(item)"></i>
        </div>
        <div class="name">
          <p>{{ item.name }}</p>
        </div>
      </div>
    </el-col>
    <el-dialog :visible="dialogFormVisible" @close="clear" :modal="false">
      <attribute-form
        :choosenModel="choosenModel"
        ref="attributeForm"
      ></attribute-form>
    </el-dialog>
  </div>
</template>

<script>
import AttributeForm from './AttributeForm.vue'
export default {
  data() {
    return {
      lineModels: [],
      dialogFormVisible: false,
      choosenModel: ''
    }
  },
  components: { AttributeForm },
  computed: {
    imgUrl: function() {
      return (
        'http://' +
        this.$store.getters.getAddress +
        ':' +
        this.$store.getters.getEndPort +
        '/api/file/connect.png'
      )
    }
  },
  methods: {
    // 根据模板文件的id初始化模板文件的内容
    init(file_id) {
      var _this = this
      _this.$axios
        .post('/model/list_all', {
          judgeNode: false,
          modelFileNode: {
            id: file_id
          }
        })
        .then(resp => {
          if (resp.data.code === 200) {
            _this.lineModels = resp.data.data
          }
        })
    },
    // 关闭后初始化
    clear() {
      var _this = this
      _this.dialogFormVisible = false
      _this.choosenModel = ''
    },
    // 添加属性
    addAttribute(item) {
      var _this = this
      _this.choosenModel = item
      _this.dialogFormVisible = true
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
    chooseLink(item) {
      var _this = this
      _this.$emit('chosenLink', item)
    }
  }
}
</script>

<style scoped>
.cover {
  width: 50px;
  height: 50px;
}
img.lineImage {
  width: 100%;
  height: 100%;
  border-radius: 50%;
}
i.el-icon-top-right {
  font-size: 40px;
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
