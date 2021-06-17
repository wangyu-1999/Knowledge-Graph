<template>
  <div>
    <el-upload
      class="upload"
      ref="upload"
      :action="imgUrl"
      :on-preview="handlePreview"
      :on-remove="handleRemove"
      :before-remove="beforeRemove"
      :on-exceed="handleExceed"
      :on-success="handleSuccess"
      :file-list="fileList"
      multiple
      :limit="1"
    >
      <div class="uploadBoard">
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将图片拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip" slot="tip">
          只能上传jpg/png文件，且不超过500kb
        </div>
      </div>
    </el-upload>
  </div>
</template>

<script>
export default {
  data() {
    return {
      fileList: [],
      url: ''
    }
  },
  methods: {
    handleRemove(file, fileList) {},
    handlePreview(file) {},
    handleExceed(files, fileList) {},
    beforeRemove(file, fileList) {},
    handleSuccess(response) {
      var _this = this
      _this.url = response
      _this.$emit('onUpload', _this.url)
      _this.$message.success('上传成功')
      _this.$refs.upload.clearFiles()
    }
  },
  computed: {
    imgUrl: function() {
      return (
        'http://' +
        this.$store.getters.getAddress +
        ':' +
        this.$store.getters.getEndPort +
        '/api/model/img'
      )
    }
  }
}
</script>

<style scoped>
.uploadBoard {
  border: solid #909399;
  padding: 15px;
  border-radius: 5%;
}
</style>
