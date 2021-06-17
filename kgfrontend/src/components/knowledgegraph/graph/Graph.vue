<template>
  <div id="container">
    <div id="info"></div>
    <div id="graph">
      <svg :fileId="fileId" :width="width" :height="height" />
    </div>
    <el-dialog
      :visible.sync="dialogFormVisible"
      @close="close"
      :append-to-body="true"
    >
      <el-form>
        <el-form-item label="新建关系">
          <el-input v-model="form.name" placeholder="请输入关系名称"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click.stop="submit">确定</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <div v-if="refreshForm">
      <el-drawer
        title="修改/删除数据"
        :visible.sync="dialogFormView"
        direction="rtl"
        @close="close"
      >
        <el-form ref="form" :model="editForm" label-width="80px">
          <el-form-item
            v-for="(domain, index) in editForm.domains"
            :label="domain.label"
            :key="index"
          >
            <el-input v-model="domain.value"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click.stop="submitForm">修改</el-button>
            <el-button @click.stop="deleteForm">删除</el-button>
          </el-form-item>
        </el-form>
      </el-drawer>
    </div>
  </div>
</template>

<script>
import * as d3 from 'd3'
export default {
  name: 'Demo',
  props: {
    fileId: Number,
    width: Number,
    height: Number,
    nodes: Array,
    edges: Array,
    chosenLink: Object
  },
  components: {},
  data() {
    return {
      dialogFormVisible: false,
      form: {
        from: '',
        to: '',
        neo4j_file_id: '',
        name: ''
      },
      editForm: {
        domains: []
      },
      nodeId: null,
      dialogFormView: false,
      refreshForm: true,
      type: null
    }
  },
  mounted() {
    this.init()
  },
  watch: {
    nodes: function() {
      this.clear()
      this.init()
    },
    edges: function() {
      this.clear()
      this.init()
    }
  },
  methods: {
    init() {
      var _this = this
      // 禁止使用右键菜单
      document.oncontextmenu = function(e) {
        e.preventDefault()
      }
      // 是否拖拽的标记
      let dragging = false
      let marge = { top: 60, bottom: 60, left: 60, right: 60 }
      let svg = d3.select('svg')
      let width = this.width ? this.width : 1500
      let height = this.height ? this.height : 1000
      let g = svg
        .append('g')
        .attr('transform', 'translate(' + marge.top + ',' + marge.left + ')')
      // 节点集
      let nodes = this.nodes
      // 边集
      let edges = this.edges
      // 调整索引
      edges.forEach(function(e) {
        let sourceNode = nodes.filter(function(n) {
          if (n.id !== undefined && n.id !== null && n.id !== '') {
            return n.id === e.from
          }
        })[0]
        let targetNode = nodes.filter(function(n) {
          if (n.id !== undefined && n.id !== null && n.id !== '') {
            return n.id === e.to
          }
        })[0]
        e.source = sourceNode
        e.target = targetNode
      })
      // 新建一个力导向图
      let forceSimulation = d3
        .forceSimulation()
        .force('link', d3.forceLink())
        .force('charge', d3.forceManyBody())
        .force('center', d3.forceCenter())
      // 生成节点数据
      forceSimulation.nodes(nodes).on('tick', ticked)
      // 生成边数据
      forceSimulation
        .force('link')
        .links(edges)
        .distance(150)
      // 设置图形中心位置
      forceSimulation
        .force('center')
        .x(width / 3)
        .y(height / 3)
      // 设置电荷力
      forceSimulation.force('charge').strength(-10)
      // 绘制边
      let links = g
        .append('g')
        .selectAll('line')
        .data(edges)
        .enter()
        .append('line')
        .attr('stroke', 'black')
        .attr('stroke-width', 1)
      // 箭头
      let marker = svg
        .append('marker')
        .attr('id', 'resolved')
        .attr('markerUnits', 'userSpaceOnUse')
        .attr('viewBox', '0 -5 10 10') //坐标系的区域
        .attr('refX', 25) //箭头坐标
        .attr('refY', 0)
        .attr('markerWidth', 12) //标识的大小
        .attr('markerHeight', 12)
        .attr('orient', 'auto') //绘制方向，可设定为：auto（自动确认方向）和 角度值
        .attr('stroke-width', 2) //箭头宽度
        .append('path')
        .attr('d', 'M0,-5L10,0L0,5') //箭头的路径
      // 给边添加箭头
      links.attr('marker-end', 'url(#resolved)')
      // 为边添加点击动作
      links.on('click.stop', click)
      // 为边添加悬停动作
      links
        .on('mouseenter', function(d) {
          if (!dragging) {
            links.attr('stroke-width', 3)
            let title = d3.select('#info').append('h2')
            d3.select('#info')
              .selectAll('p')
              .remove()
            let info = d3.select('#info')
            for (let key in d) {
              if (
                key !== 'id' &&
                key !== 'index' &&
                key !== 'neo4j_file_id' &&
                key !== 'noe4j_img' &&
                key !== 'vx' &&
                key !== 'vy' &&
                key !== 'x' &&
                key !== 'y' &&
                key !== 'fx' &&
                key !== 'fy' &&
                key !== 'from' &&
                key !== 'to' &&
                key !== 'source' &&
                key !== 'target'
              ) {
                if (key === 'neo4j_model_name') {
                  info.append('p').text('模块' + '  :  ' + d[key])
                } else if (key === 'name') {
                  title.text(d[key])
                } else {
                  info.append('p').text(key + '  :  ' + d[key])
                }
              }
            }
          }
        })
        .on('mouseleave', function(d) {
          links.attr('stroke-width', 1)
          d3.select('#info')
            .select('h2')
            .remove()
          d3.select('#info')
            .selectAll('p')
            .remove()
        })
      // 边上的文字
      let linksText = g
        .append('g')
        .selectAll('text')
        .data(edges)
        .enter()
        .append('text')
        .attr('fill', '#D2B48C')
        .text(function(d) {
          return d.name
        })
      linksText.on('click.stop', click)
      linksText
        .on('mouseenter', function(d) {
          if (!dragging) {
            links.attr('stroke-width', 3)
            let title = d3.select('#info').append('h2')
            d3.select('#info')
              .selectAll('p')
              .remove()
            let info = d3.select('#info')
            for (let key in d) {
              if (
                key !== 'id' &&
                key !== 'index' &&
                key !== 'neo4j_file_id' &&
                key !== 'noe4j_img' &&
                key !== 'vx' &&
                key !== 'vy' &&
                key !== 'x' &&
                key !== 'y' &&
                key !== 'fx' &&
                key !== 'fy' &&
                key !== 'from' &&
                key !== 'to' &&
                key !== 'source' &&
                key !== 'target'
              ) {
                if (key === 'neo4j_model_name') {
                  info.append('p').text('模块' + '  :  ' + d[key])
                } else if (key === 'name') {
                  title.text(d[key])
                } else {
                  info.append('p').text(key + '  :  ' + d[key])
                }
              }
            }
          }
        })
        .on('mouseleave', function(d) {
          links.attr('stroke-width', 1)
          d3.select('#info')
            .select('h2')
            .remove()
          d3.select('#info')
            .selectAll('p')
            .remove()
        })
      // 节点
      let gs = g
        .selectAll('.circleText')
        .data(nodes)
        .enter()
        .append('g')
        .attr('transform', function(d) {
          let cirX = d.x
          let cirY = d.y
          return 'translate(' + cirX + ',' + cirY + ')'
        })
        .call(
          d3
            .drag()
            .on('start', started)
            .on('drag', dragged)
            .on('end', ended)
        )
      // 节点替换为图片
      let redius = 20
      let imgWidth = 40
      let imgHeight = 40
      gs.append('circle')
        .attr('r', redius)
        .attr('fill', function(d, i) {
          let defs = svg.append('defs').attr('id', 'imgdefs')
          let catpattern = defs
            .append('pattern')
            .attr('id', 'catpattern' + i)
            .attr('patternUnits', 'objectBoundingBox')
            .attr('height', 1)
            .attr('width', 1)
          if (d.noe4j_img !== undefined) {
            catpattern
              .append('image')
              .attr('width', imgWidth)
              .attr('height', imgHeight)
              .attr('border-radius', 0.5)
              .attr('x', -(imgWidth / 2 - redius))
              .attr('y', -(imgHeight / 2 - redius))
              .attr('xlink:href', function() {
                return d.noe4j_img
              })
              .attr('preserveAspectRatio', 'none')
            return 'url(#catpattern' + i + ')'
          } else {
            return 'black'
          }
        })
      // 文字
      gs.append('text')
        .attr('fill', '#DDA0DD')
        .attr('x', 25)
        .attr('y', 0)
        .attr('dy', 0)
        .text(function(d) {
          return d.name
        })

      // 设置拖拽
      {
        let mousedownNode = null,
          mouseupNode = null
        gs.on('mousedown', function(d) {
          mousedownNode = d
        }).on('mouseup', function(d) {
          if (mousedownNode) {
            mouseupNode = d
            if (mouseupNode == mousedownNode) {
              mousedownNode = null
              mouseupNode = null
              return
            }
            _this.addLink(mousedownNode.id, mouseupNode.id)
            mousedownNode = null
            mouseupNode = null
            return
          }
        })
      }
      // 设置点击节点
      gs.on('click.stop', click)
      // 为节点添加悬停动作
      gs.on('mouseenter', function(d) {
        if (!dragging) {
          let title = d3.select('#info').append('h2')
          d3.select('#info')
            .selectAll('p')
            .remove()
          let info = d3.select('#info')
          for (let key in d) {
            if (
              key !== 'id' &&
              key !== 'index' &&
              key !== 'neo4j_file_id' &&
              key !== 'noe4j_img' &&
              key !== 'vx' &&
              key !== 'vy' &&
              key !== 'x' &&
              key !== 'y' &&
              key !== 'fx' &&
              key !== 'fy'
            ) {
              if (key === 'neo4j_model_name') {
                info.append('p').text('模块' + '  :  ' + d[key])
              } else if (key === 'name') {
                title.text(d[key])
              } else {
                info.append('p').text(key + '  :  ' + d[key])
              }
            }
          }
        }
      }).on('mouseleave', function(d) {
        d3.select('#info')
          .select('h2')
          .remove()
        d3.select('#info')
          .selectAll('p')
          .remove()
      })
      // ticked
      function ticked() {
        links
          .attr('x1', function(d) {
            return d.source.x
          })
          .attr('y1', function(d) {
            return d.source.y
          })
          .attr('x2', function(d) {
            return d.target.x
          })
          .attr('y2', function(d) {
            return d.target.y
          })
        linksText
          .attr('x', function(d) {
            return (d.source.x + d.target.x) / 2
          })
          .attr('y', function(d) {
            return (d.source.y + d.target.y) / 2
          })
        gs.attr('transform', function(d) {
          return 'translate(' + d.x + ',' + d.y + ')'
        })
      }
      // drag
      function started(d) {
        dragging = true
        if (!d3.event.active) {
          forceSimulation.alphaTarget(0.8).restart() // 设置衰减系数，对节点位置移动过程的模拟，数值越高移动越快，数值范围[0, 1]
        }
        d.fx = d.x
        d.fy = d.y
      }
      function dragged(d) {
        d.fx = d3.event.x
        d.fy = d3.event.y
      }
      function ended(d) {
        dragging = false
        if (!d3.event.active) {
          forceSimulation.alphaTarget(0)
        }
        d.fx = null
        d.fy = null
      }
      function click(d) {
        _this.type = null
        _this.editForm = {
          domains: []
        }
        _this.refreshForm = false
        for (let key in d) {
          if (
            key !== 'index' &&
            key !== 'neo4j_file_id' &&
            key !== 'vx' &&
            key !== 'vy' &&
            key !== 'x' &&
            key !== 'y' &&
            key !== 'fx' &&
            key !== 'fy' &&
            key !== 'from' &&
            key !== 'to' &&
            key !== 'target' &&
            key !== 'neo4j_model_name'
          ) {
            if (key === 'id') {
              _this.nodeId = d[key]
            } else if (key === 'name') {
              let temp = { label: '名称', value: d[key] }
              _this.editForm.domains.push(temp)
            } else if (key === 'source') {
              _this.type = 'relation'
            } else if (key === 'noe4j_img') {
              _this.type = 'node'
            } else {
              let temp = { label: key, value: d[key] }
              _this.editForm.domains.push(temp)
            }
          }
        }
        _this.refreshForm = true
        _this.dialogFormView = true
      }
    },
    // 清除内容
    clear() {
      let svg = d3.select('svg')
      svg.selectAll('*').remove()
    },
    // 添加关系
    addLink(source, target) {
      var _this = this
      _this.form.from = source
      _this.form.to = target
      _this.form.neo4j_file_id = _this.fileId
      _this.dialogFormVisible = true
    },
    // 提交添加关系表格
    submit() {
      var _this = this
      if (_this.chosenLink !== {}) {
        _this.$axios
          .post('/model/init_attribute', _this.chosenLink)
          .then(resp => {
            if (resp.data.code === 200) {
              for (let attr of resp.data.data) {
                _this.form[attr.key] = ''
              }
            }
            _this.$axios.post('/neo4j/add_relation', _this.form).then(resp => {
              if (resp.data.code === 200) {
                _this.$emit('fresh', resp.data.data)
                _this.form = {
                  from: '',
                  to: '',
                  neo4j_file_id: '',
                  name: ''
                }
                _this.dialogFormVisible = false
              }
            })
          })
      }
    },
    // 关闭表格
    close() {
      var _this = this
      _this.dialogFormVisible = false
      _this.dialogFormView = false
    },
    submitForm() {
      var _this = this
      let tempForm = {
        id: _this.nodeId
      }
      for (let item of _this.editForm.domains) {
        if (item.label === '名称') {
          tempForm.name = ''
          tempForm.name = item.value
        } else {
          tempForm[item.label] = item.value
        }
      }
      if (_this.type === 'node') {
        _this.$axios.post('/neo4j/set_node', tempForm).then(resp => {
          if (resp.data.code === 200) {
            _this.$axios
              .post('/neo4j/list', {
                neo4j_file_id: _this.fileId
              })
              .then(resp => {
                if (resp.data.code === 200) {
                  _this.$emit('fresh', resp.data.data)
                }
              })
          }
        })
      } else {
        _this.$axios.post('/neo4j/set_relation', tempForm).then(resp => {
          if (resp.data.code === 200) {
            _this.$axios
              .post('/neo4j/list', {
                neo4j_file_id: _this.fileId
              })
              .then(resp => {
                if (resp.data.code === 200) {
                  _this.$emit('fresh', resp.data.data)
                }
              })
          }
        })
      }
      _this.dialogFormView = false
    },
    deleteForm() {
      var _this = this
      let tempForm = {
        id: _this.nodeId
      }
      if (_this.type === 'node') {
        _this.$axios.post('/neo4j/delete_node', tempForm).then(resp => {
          if (resp.data.code === 200) {
            _this.$axios
              .post('/neo4j/list', {
                neo4j_file_id: _this.fileId
              })
              .then(resp => {
                if (resp.data.code === 200) {
                  _this.$emit('fresh', resp.data.data)
                }
              })
          }
        })
      } else {
        _this.$axios.post('/neo4j/delete_relation', tempForm).then(resp => {
          if (resp.data.code === 200) {
            _this.$axios
              .post('/neo4j/list', {
                neo4j_file_id: _this.fileId
              })
              .then(resp => {
                if (resp.data.code === 200) {
                  _this.$emit('fresh', resp.data.data)
                }
              })
          }
        })
      }
      _this.dialogFormView = false
    }
  }
}
</script>
<style scoped>
#info {
  position: fixed;
  top: 250px;
  left: 300px;
  color: orangered;
  font-size: x-small;
}

#graph {
  overflow: hidden;
  height: 1000px;
  width: 1500px;
}
</style>
