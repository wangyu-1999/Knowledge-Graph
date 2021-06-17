<template>
  <div>
    <el-form
      :model="formData"
      ref="formData"
      label-width="80px"
      class="form-dynamic"
      @close="clear"
    >
      <el-form-item v-if="choosenModel.judgeNode" label="图片"
        ><img-upload @onUpload="changeUrl"></img-upload
      ></el-form-item>
      <el-form-item
        v-for="(domain, index) in formData.domains"
        :label="'属性' + (index + 1)"
        :key="domain.key"
        :prop="'domains.' + index + '.value'"
        :rules="[
          { required: true, message: '内容不能为空', trigger: 'blur' },
          {
            pattern: /^[^0-9\u0022\u0027 ][^\u0022\u0027]+$/,
            message: '不能以数字引号空格开头',
            trigger: 'blur'
          },
          {
            validator: validate,
            trigger: 'blur'
          }
        ]"
      >
        <el-input v-model="domain.value"></el-input>
        <a
          class="remove-item"
          v-show="formData.domains.length > 1"
          @click.prevent="removeDomain(domain)"
          ><i class="el-icon-close"></i
        ></a>
      </el-form-item>
      <el-form-item class="submit-btn">
        <el-button type="primary" @click="submitForm('formData')"
          >提交</el-button
        >
        <el-button @click="addDomain">新增一项</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import ImgUpload from './ImgUpload.vue'
export default {
  name: 'Form',
  props: ['choosenModel'],
  components: {
    ImgUpload
  },
  mounted() {
    if (this.choosenModel !== '') {
      this.init(this.choosenModel)
    }
  },
  watch: {
    choosenModel: function() {
      if (this.choosenModel !== '') {
        this.init(this.choosenModel)
      }
    }
  },
  data() {
    return {
      formData: {
        domains: [
          {
            value: ''
          }
        ]
      },
      validate: (rule, value, callback) => {
        if (
          value === 'id' ||
          value === 'neo4j_file_id' ||
          value === 'name' ||
          value === 'source' ||
          value === 'target' ||
          value === 'from' ||
          value === 'to' ||
          value === 'neo4j_model_name' ||
          value === 'noe4j_img' ||
          value === 'fx' ||
          value === 'fy' ||
          value === 'vx' ||
          value === 'vy' ||
          value === 'x' ||
          value === 'y' ||
          value === 'index' ||
          value === '名称'
        ) {
          callback('保留的关键字不能作为属性名')
        } else {
          callback()
        }
      }
    }
  },
  methods: {
    clear() {
      var _this = this
      _this.formData = {
        domains: [
          {
            value: ''
          }
        ]
      }
      _this.$emit('changed', _this.choosenModel.modelFileNode.id)
    },
    /*增加表单项*/
    addDomain() {
      var _this = this
      _this.formData.domains.push({
        value: ''
      })
    },
    /*删除表单项*/
    removeDomain(item) {
      var _this = this
      let index = _this.formData.domains.indexOf(item)
      if (index !== -1) {
        _this.formData.domains.splice(index, 1)
      }
    },
    /*格式化表单数据*/
    formatData(data) {
      let dataSet = new Set()
      data.forEach((item, index) => {
        dataSet.add(item.value)
      })
      return dataSet
    },
    /*提交表单*/
    submitForm(formName) {
      var _this = this
      _this.$refs[formName].validate(valid => {
        if (valid) {
          let dataSet = _this.formatData(_this.formData.domains)
          let sendSet = []
          for (let data of dataSet) {
            sendSet.push({ key: data, model: { id: _this.choosenModel.id } })
          }
          _this.$axios.post('/model/add_attribute', sendSet).then(resp => {
            if (resp.data.code === 200) {
              // console.log(resp.data.data)
              _this.$message({
                type: 'success',
                message: '添加成功'
              })
              _this.$emit('changed', _this.choosenModel.modelFileNode.id)
            }
          })
        } else {
          _this.$message({
            type: 'warning',
            message: '存在非法的输入'
          })
          _this.$emit('changed', _this.choosenModel.modelFileNode.id)
          return false
        }
      })
    },
    /*初始化表单*/
    init(model) {
      var _this = this
      _this.formData = {
        domains: []
      }
      _this.$axios.post('/model/init_attribute', model).then(resp => {
        if (resp.data.code === 200) {
          if (resp.data.data.length === 0) {
            _this.formData.domains.push({
              value: ''
            })
          } else {
            for (let attritube of resp.data.data) {
              _this.formData.domains.push({
                value: attritube.key
              })
            }
          }
        }
      })
    },
    /*修改图片url*/
    changeUrl(url) {
      var _this = this
      _this.$axios
        .post('/model/change_url', {
          id: _this.choosenModel.id,
          img: url
        })
        .then(resp => {
          if (resp.data.code === 200) {
            _this.$emit('changed', _this.choosenModel.modelFileNode.id)
          }
        })
    }
  }
}
</script>
<style scoped>
.el-input {
  width: 95%;
}
.remove-item {
  color: red;
}
.submit-btn {
  text-align: center;
  margin-left: -60px;
}
</style>
