<!-- 注册登录页面 -->
<template>
  <body id="poster">
    <div id="login-container">
      <el-form>
        <div id="login_title">
          <p>系统登录</p>
        </div>
        <el-form-item>
          <el-input
            type="text"
            v-model="loginForm.username"
            placeholder="账号"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-input
            type="password"
            v-model="loginForm.password"
            placeholder="密码"
            show-password
          ></el-input>
        </el-form-item>
        <div id="login_button_box">
          <el-form-item>
            <el-button type="primary" v-on:click="register">注册</el-button>
            <el-button type="primary" v-on:click="login">登录</el-button>
          </el-form-item>
        </div>
      </el-form>
    </div>
  </body>
</template>

<script>
export default {
  name: 'Login',
  data() {
    return {
      // 这里可以设置默认的登陆名称和密码
      loginForm: {
        username: '',
        password: ''
      },
      responseResult: []
    }
  },
  methods: {
    /**
    收到后端返回的成功代码时，
    触发 store 中的 login() 方法，
    把 loginForm 对象传递给 store 中的 user 对象
    */
    login() {
      var _this = this
      _this.$axios
        .post('/login', {
          username: _this.loginForm.username,
          password: _this.loginForm.password
        })
        .then(resp => {
          if (resp.data.code === 200) {
            _this.$alert('登录成功', '提示', {
              confirmButtonText: '确定'
            })
            _this.$store.commit('login', _this.loginForm)
            let path = _this.$route.query.redirect
            _this.$router.replace({
              path: path === '/' || path === undefined ? '/index' : path
            })
          } else {
            _this.$alert(resp.data.message, '提示', {
              confirmButtonText: '确定'
            })
          }
        })
        .catch(failResponse => {})
    },
    register() {
      var _this = this
      _this.$axios
        .post('/register', {
          username: _this.loginForm.username,
          password: _this.loginForm.password
        })
        .then(resp => {
          if (resp.data.code === 200) {
            _this.$alert('注册成功', '提示', {
              confirmButtonText: '确定'
            })
            _this.$store.commit('login', _this.loginForm)
            let path = _this.$route.query.redirect
            _this.$router.replace({
              path: path === '/' || path === undefined ? '/index' : path
            })
          } else {
            _this.$alert(resp.data.message, '提示', {
              confirmButtonText: '确定'
            })
          }
        })
        .catch(failResponse => {})
    }
  }
}
</script>

<style scoped>
/*这里不能用body，只能用#poster，否则会影响其他地方的代码*/
#poster {
  background: url('../../assets/background.jpg') no-repeat;
  background-position: center;
  height: 100%;
  width: 100%;
  background-size: cover;
  position: fixed;
  margin: 0px;
  padding: 0px;
}
#login-container {
  border-radius: 15px;
  margin: 220px auto;
  width: 350px;
  padding: 35px 35px 15px 35px;
  background: white;
}
#login_title {
  margin: 0px auto 40px auto;
  text-align: center;
  font-weight: 500;
  font-size: 20px;
}
#login_button_box {
  margin: 0, auto;
  text-align: center;
}
</style>
