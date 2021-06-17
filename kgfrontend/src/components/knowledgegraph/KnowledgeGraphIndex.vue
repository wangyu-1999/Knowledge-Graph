<!-- 除去菜单栏主页内容 -->
<template>
  <div class="page">
    <el-row :gutter="5">
      <el-col :span="4">
        <div class="leftSide">
          <p class="leftSideTitle">知识图谱编辑</p>
          <!-- 左边的菜单栏 -->
          <div class="letfSideMenu">
            <el-button type="text" size="medium" @click.stop="openFileList"
              >选择文件
            </el-button>
            <br />
            <el-button type="text" size="medium" @click.stop="openModelList"
              >模板选择
            </el-button>
            <br />
            <el-button type="text" size="medium" @click.stop="openSubGraphList"
              >子图存取
            </el-button>
            <br />
            <el-button type="text" size="medium" @click.stop="clickFullscreen"
              >图谱全屏
            </el-button>
          </div>
          <!-- 文件选择的组件 -->
          <file-list ref="fileList" @clickNode="checkFile"></file-list>
          <!-- 模板选择的组件 -->
          <model-list
            ref="modelList"
            @fresh="fresh"
            :fileId="fileId"
            @chosenLink="getLink"
          ></model-list>
          <!-- 子图列表 -->
          <sub-graph-list
            ref="subgraphList"
            @addSubGraph="addSubGraph"
            @checkSubGraph="checkSubGraph"
          ></sub-graph-list>
        </div>
      </el-col>

      <el-col :span="0.5"
        ><div>
          <el-divider direction="vertical"></el-divider>
        </div>
      </el-col>

      <el-col :span="19.5">
        <div class="rightSide">
          <!-- 信息显示的区域 -->
          <div id="showInfo">
            <p><span>选择文件:</span>{{ fileName }}</p>
            <p><span>选择关系:</span>{{ chosenLink.name }}</p>
          </div>

          <!-- 子图相关的表单 -->
          <div id="subgraph">
            <el-form :model="subgraphData" :inline="true">
              <el-form-item label="实体名称">
                <el-input v-model="subgraphData.name"></el-input>
              </el-form-item>
              <el-form-item label="跳数">
                <el-input v-model="subgraphData.jumpNum"></el-input>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="onSubmitSubgraph"
                  >查询子图</el-button
                >
              </el-form-item>
            </el-form>
          </div>
          <!-- 知识图谱的相关组件 -->
          <div id="kgGraph">
            <graph
              ref="graph"
              :fileId="fileId"
              :width="width"
              :height="height"
              :nodes="nodes"
              :edges="edges"
              :chosenLink="chosenLink"
              @fresh="fresh"
            ></graph>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import FileList from './fileList/FileList.vue'
import screenfull from 'screenfull'
import ModelList from './modelList/ModelList.vue'
import Graph from './graph/Graph.vue'
import SubGraphList from './subgraph/SubGraphList.vue'
export default {
  name: 'Index',
  data() {
    return {
      // 选择知识图谱文件编号
      fileId: null,
      // 知识图谱的名称
      fileName: null,
      // 选择的关系模板
      chosenLink: {},
      // 向子组件传递，绘制知识图谱的宽度
      width: 1500,
      // 向子组件传递，绘制知识图谱的高度
      height: 1000,
      // 节点集
      nodes: [],
      // 边集
      edges: [],
      // 子图相关属性
      subgraphData: {
        // 实体名称
        name: null,
        // 跳数
        jumpNum: null
      }
    }
  },
  components: { FileList, Graph, ModelList, SubGraphList },
  mounted() {
    // 全屏时候需要调整宽高
    window.onresize = () => {
      if (screenfull.isFullscreen) {
        this.width = window.screen.width
        this.height = window.screen.height
      } else {
        this.width = 1500
        this.height = 1000
      }
    }
  },
  methods: {
    // 打开文件列表
    openFileList() {
      this.$refs.fileList.drawerVisible = true
    },
    // 打开模板列表
    openModelList() {
      this.$refs.modelList.drawerVisible = true
    },
    // 打开子图列表
    openSubGraphList() {
      var _this = this
      if (_this.fileId === null || _this.fileId === '') {
        _this.$message({
          message: '请选择文件后加载子图',
          type: 'warning'
        })
        return
      }
      _this.$refs.subgraphList.fileId = _this.fileId
      _this.$refs.subgraphList.drawerVisible = true
    },
    // 图谱全屏
    clickFullscreen() {
      var _this = this
      const element = document.getElementById('kgGraph')
      if (!screenfull.isEnabled) {
        _this.$message({
          message: '浏览器不支持',
          type: 'warning'
        })
        return false
      }
      screenfull.request(element)
    },
    // 选择节点后将节点的信息通过回调函数传过来
    checkFile(nodeData) {
      var _this = this
      _this.fileId = nodeData.id
      _this.fileName = nodeData.label
      _this.subgraphData.name = null
      _this.subgraphData.jumpNum = null
      if (nodeData.judgeFolder === false) {
        _this.$axios
          .post('/neo4j/list', {
            neo4j_file_id: nodeData.id
          })
          .then(resp => {
            if (resp.data.code === 200) {
              _this.nodes = resp.data.data.nodes
              _this.edges = resp.data.data.links
            }
          })
      }
    },
    // 更新力导向图数据
    fresh(val) {
      var _this = this
      _this.nodes = val.nodes
      _this.edges = val.links
    },
    // 获得选中的关系
    getLink(val) {
      var _this = this
      _this.chosenLink = val
      // console.log(val)
    },
    // 点击子图表单按钮
    onSubmitSubgraph() {
      var _this = this
      if (_this.fileId === null) {
        _this.$message({
          message: '未选中文件',
          type: 'error'
        })
        return
      }
      if (_this.subgraphData.name === null || _this.subgraphData.name === '') {
        _this.$message({
          message: '未输入节点的内容',
          type: 'error'
        })
        return
      }
      if (
        _this.subgraphData.jumpNum === null ||
        _this.subgraphData.jumpNum === ''
      ) {
        _this.$message({
          message: '未输入跳数',
          type: 'error'
        })
        return
      }
      if (parseInt(_this.subgraphData.jumpNum) < 1) {
        _this.$message({
          message: '不合法的跳数参数',
          type: 'error'
        })
        return
      }
      _this.$axios
        .post('/neo4j/find_subgraph/', {
          id: _this.fileId,
          name: _this.subgraphData.name,
          jumpNum: _this.subgraphData.jumpNum
        })
        .then(resp => {
          if (resp.data.code === 200) {
            _this.nodes = resp.data.data.nodes
            _this.edges = resp.data.data.links
          }
        })
    },
    // 添加子图
    addSubGraph(name) {
      var _this = this
      if (_this.subgraphData.name === null || _this.subgraphData.name === '') {
        _this.$message({
          message: '未输入节点的内容',
          type: 'error'
        })
        return
      }
      if (
        _this.subgraphData.jumpNum === null ||
        _this.subgraphData.jumpNum === ''
      ) {
        _this.$message({
          message: '未输入跳数',
          type: 'error'
        })
        return
      }
      if (parseInt(_this.subgraphData.jumpNum) < 1) {
        _this.$message({
          message: '不合法的跳数参数',
          type: 'error'
        })
        return
      }
      _this.$axios
        .post('/subgraph/add', {
          name: name,
          fileNode: {
            id: _this.fileId
          }
        })
        .then(resp => {
          if (resp.data.code === 200) {
            _this.$axios
              .post('/neo4j/save_subgraph', {
                id: _this.fileId,
                name: _this.subgraphData.name,
                jumpNum: _this.subgraphData.jumpNum,
                subgraphName: name
              })
              .then(resp2 => {
                if (resp2.data.code === 200) {
                  _this.nodes = resp2.data.data.nodes
                  _this.edges = resp2.data.data.links
                  _this.$refs.subgraphList.tagList.push(name)
                }
              })
          }
        })
    },
    // 选中子图
    checkSubGraph(name) {
      var _this = this
      _this.$axios
        .post('/subgraph/select', {
          name: name,
          fileNode: {
            id: _this.fileId
          }
        })
        .then(resp => {
          if (resp.data.code === 200) {
            _this.nodes = resp.data.data.nodes
            _this.edges = resp.data.data.links
          }
        })
    }
  }
}
</script>

<style scoped>
div.page {
  width: 1520px;
}
div.leftSide {
  /*左边栏目*/
  text-align: center;
}
p.leftSideTitle {
  /*左边栏目的标题*/
  font-size: 18px;
  margin-top: 30px;
  padding-bottom: 25px;
  border-bottom-style: solid;
}
.el-divider--vertical {
  /*分割线高度*/
  height: 720px;
}
div.rightSide {
  /*右边主要显示区域*/
  background-color: white;
  margin-top: 0px;
  padding-top: 0px;
  width: 1230px;
  height: 700px;
  overflow: scroll;
}
#subgraph {
  /* 子图表单 */
  position: relative;
  left: 250px;
  top: 30px;
}
#showInfo {
  /* 信息显示 */
  float: left;
}
#kgGraph {
  /* 知识图谱 */
  background: white;
}
</style>
