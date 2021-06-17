<template>
  <div>
    <div id="info">
      <h1 style="text-align:center">使用说明</h1>
      <h1 id="登录">登录</h1>

      <p>
        当进入页面后，将自动检测用户是否已经登录，如未登录则显示登录/注册选项卡，要求用户进行登录或注册操作。用户如完成操作或希望使用其他用户登录，则可以进行登出操作。
      </p>

      <p class="imgContainer">
        <img
          src="../../assets/info_img/dc022730ce4dfda4cbb6c835abeaf451.png"
          alt=""
        />
      </p>

      <p class="caption">图 1使用未注册账号登录</p>

      <p>系统会弹出相应的提示信息显示无法登录：</p>

      <p class="imgContainer">
        <img
          src="../../assets/info_img/cf083315279064d3593e48abd9e52a64.png"
          alt=""
        />
      </p>

      <p class="caption">图 2无法登录的提示</p>

      <p>点击注册的话会有相应的提示：</p>

      <p class="imgContainer">
        <img
          src="../../assets/info_img/5b5bd0bdea5cea856db222e786a595cf.png"
          alt=""
        />
      </p>

      <p class="caption">图 3注册成功的提示</p>

      <p>注册成功之后可以使用对应的账号登录：</p>

      <p class="imgContainer">
        <img
          src="../../assets/info_img/c03552935d2154d162d9a47a0ed45157.png"
          alt=""
        />
      </p>

      <p class="caption">图 4登陆成功的提示</p>

      <p>登录系统后可以登出系统：</p>

      <p class="imgContainer">
        <img
          src="../../assets/info_img/d64ccab8d7fe29a849ea03ec7bca9cd5.png"
          alt=""
        />
      </p>

      <p class="caption">图 5通过功能选项登出系统</p>

      <p class="imgContainer">
        <img
          src="../../assets/info_img/bff87be3353bae9f1752ea360fa1f3b2.png"
          alt=""
        />
      </p>

      <p class="caption">图 6通过退出的图标退出系统</p>

      <h1 id="整体界面说明">整体界面说明</h1>

      <p>
        登录成功后可以通过顶栏的选择功能-&gt;知识图谱编辑进入到知识图谱编辑的功能当中：
      </p>

      <p class="imgContainer">
        <img
          src="../../assets/info_img/7043d02aef0dd64b9d21c13b2f2baa85.png"
          alt=""
        />
      </p>

      <p class="caption">图 7进入知识图谱编辑功能</p>

      <p>
        图8是本工具的实现的界面样式，其中，顶栏是选择功能的对应导航栏，左侧可以实现选择文件、模板选择、子图存取、图谱全屏的相应功能，右侧图谱展示和交互区域，图谱中的实体和关系展示的样式将与相关联的图元模板样式相同。
      </p>

      <p>
        图谱上方会展示当前选择的文件和关系名称，顶部右侧有查询子图的搜索框。
      </p>

      <p class="imgContainer">
        <img
          src="../../assets/info_img/b414171325b034c759637775a93262f2.png"
          alt=""
        />
      </p>

      <p class="caption">图 8整体界面</p>

      <h1 id="用户文件系统说明">用户文件系统说明</h1>

      <h2 id="31文件系统展示方式说明">文件系统展示方式说明</h2>

      <p>
        文件以层叠的方式展示，并可以根据用户的需要折叠展开某一文件夹下的子文件系统。图9显示文件系统的展示效果。
      </p>

      <p class="imgContainer">
        <img
          src="../../assets/info_img/dcc4b6e98be892fe2320dae0c078a453.png"
          alt=""
        />
      </p>

      <p class="caption">图 9文件目录的展示效果</p>

      <p>使用搜索框可以对文件名进行模糊查询，方便快捷地找到需要的文件：</p>

      <p class="imgContainer">
        <img
          src="../../assets/info_img/fda9b6279eb64c90f298a883bf442e2b.png"
          alt=""
        />
      </p>

      <p class="caption">图 10模糊查询</p>

      <h2 id="32文件系统编辑功能说明">文件系统编辑功能说明</h2>

      <p>
        文件系统的编辑功能包括在任意层级下创建新文件或文件夹，删除文件或文件夹以及对文件或文件夹重命名。
      </p>

      <p>
        新建文件或者文件夹通过点击对应父文件夹后面的添加按钮，然后填写对应的信息，点击确认后完成新文件或者新文件夹的创建，其中新文件或者新文件夹是通过切换按钮来实现的。
      </p>

      <p class="imgContainer">
        <img
          src="../../assets/info_img/86b997a2ab8b4f40efa11fe89f073320.png"
          alt=""
        />
      </p>

      <p class="caption">图 11添加新文件的表单</p>

      <p class="imgContainer">
        <img
          src="../../assets/info_img/98e66f43e46fe7e569112970636de9b5.png"
          alt=""
        />
      </p>

      <p class="caption">图 12切换为添加新文件夹</p>

      <p>
        删除文件夹或文件通过点击对应文件或者文件夹之后的删除按钮来实现。点击后会弹出操作的提醒弹窗，确认后实现删除。
      </p>

      <p class="imgContainer">
        <img
          src="../../assets/info_img/a7c7f61f08c47e2360dd42cdfd56102d.png"
          alt=""
        />
      </p>

      <p class="caption">图 13删除的确认信息</p>

      <p>
        重命名文件或文件夹通过点击对应文件或者文件夹之后的重命名按钮来实现，其中重命名时对应项目的类型无法修改。
      </p>

      <h1 id="图元模板系统说明">图元模板系统说明</h1>

      <h2 id="41-图元模板目录说明">图元模板目录说明</h2>

      <p>
        为了更好的管理对应的图元，项目提供了包含图元的模板，图元模板和文件系统类似，
      </p>

      <p class="imgContainer">
        <img
          src="../../assets/info_img/17e66f285869770a80c39946601cce2b.png"
          alt=""
        />
      </p>

      <p class="caption">图 14图元模板系统</p>

      <h2 id="42-图元编辑说明">图元编辑说明</h2>

      <p>点击对应的图元模板后，就可以对图元进行编辑：</p>

      <p class="imgContainer">
        <img
          src="../../assets/info_img/b70724fb6c8491a6893f3b2882365749.png"
          alt=""
        />
      </p>

      <p class="caption">图 15图元编辑模板</p>

      <p>
        首先，在节点模板或者关系模板下点击添加模板，就可以添加一个对应的图元模板。
      </p>

      <p class="imgContainer">
        <img
          src="../../assets/info_img/8205428d051828e201b449313fdf28c3.png"
          alt=""
        />
      </p>

      <p class="caption">图 16新建模板</p>

      <p>点击对应模板的删除按钮，可以删除对应的图元。</p>

      <p class="imgContainer">
        <img
          src="../../assets/info_img/ffa3d767b924c00f69e4c81c8fd849ab.png"
          alt=""
        />
      </p>

      <p class="caption">图 17删除成功的提示</p>

      <p>
        点击对应图元的设置按钮，可以对图元的属性进行设置，可以新增属性，或者对属性进行重命名，也可以上传图片来设置图元在图谱中的展示样式：
      </p>

      <p class="imgContainer">
        <img
          src="../../assets/info_img/33d06afea283e5f04a7d7c1edbdc835d.png"
          alt=""
        />
      </p>

      <p class="caption">图 18图元属性设置</p>

      <h1 id="知识图谱编辑说明">知识图谱编辑说明</h1>

      <h2 id="51图谱界面说明">图谱界面说明</h2>

      <p>
        知识图谱在页面的右下方展示，其中节点用紫色的文字标识，关系用黄色的文字表示，当鼠标悬停在节点或者关系的上方时，会在左侧展示对应的属性值：
      </p>

      <p class="imgContainer">
        <img
          src="../../assets/info_img/6daef138fc20d7b46f7b8ce00332a84b.png"
          alt=""
        />
      </p>

      <p class="caption">图 19图谱界面</p>

      <p class="imgContainer">
        <img
          src="../../assets/info_img/db7aa7c178f19e35f12001d543c3d119.png"
          alt=""
        />
      </p>

      <p class="caption">图 20悬停展示属性</p>

      <h2 id="52图谱编辑说明">图谱编辑说明</h2>

      <p>除了悬停查看图谱节点属性，图谱也支持增、删、改等编辑操作。</p>

      <p>新增节点点击对应节点的添加按钮，然后输入节点的名称即可：</p>

      <p class="imgContainer">
        <img
          src="../../assets/info_img/d910def1dfaaf98fac18908ab48e7439.png"
          alt=""
        />
      </p>

      <p class="caption">图 21对应图元的添加按钮</p>

      <p class="imgContainer">
        <img
          src="../../assets/info_img/77c20c7d8d45e746750f9b00bbd6493d.png"
          alt=""
        />
      </p>

      <p class="caption">图 22填写新建节点的名称</p>

      <p>
        <em
          >新建关系节点需要选中对应的关系，然后按住鼠标右键从源节点拖拽至目标节点放开</em
        >，然后填写新建关系的名称即可：
      </p>

      <p class="imgContainer">
        <img
          src="../../assets/info_img/e1674d44d082cfca1a508a9008abd9f1.png"
          alt=""
        />
      </p>

      <p class="caption">图 23填写新建关系的名称</p>

      <p>
        节点和关系的属性修改和删除，都是通过鼠标左键单击对应的节点和关系，然后会出现属性的表单，通过修改表单内容或者点击删除按钮可以实现修改属性或者删除图元：
      </p>

      <p class="imgContainer">
        <img
          src="../../assets/info_img/9a10df116cf53eef3708b66927062d7a.png"
          alt=""
        />
      </p>

      <p class="caption">图 24单击后可以修改属性或者删除节点</p>

      <h1 id="图谱抽取">图谱抽取</h1>

      <p>
        可以在这一区域对原始图谱进行抽取，需要输入实体的名称和需要抽取的跳数（层数），然后点击查询子图。
      </p>

      <p class="imgContainer">
        <img
          src="../../assets/info_img/e78e820b41d288959b4a2adec7c409c6.png"
          alt=""
        />
      </p>

      <p class="caption">图 25子图查询</p>

      <p>子图查询完成之后，点击右侧的子图存取按钮，</p>

      <p class="imgContainer">
        <img
          src="../../assets/info_img/9cbdd7cab005347f5f8f0768d996d1d6.png"
          alt=""
        />
      </p>

      <p class="caption">图 26子图存取</p>

      <p>
        依次点击”添加子图”按钮-&gt;在输入框中输入名称即可完成子图保存的过程。
      </p>

      <p class="imgContainer">
        <img
          src="../../assets/info_img/f84f5f64856d6fc6d0351e3daf4ce2e3.png"
          alt=""
        />
      </p>

      <p class="caption">图 27添加子图</p>

      <p class="imgContainer">
        <img
          src="../../assets/info_img/158d3d6ad1c01fc9a35472738a90815a.png"
          alt=""
        />
      </p>

      <p class="caption">图 28点击后在输入框中输入名称</p>

      <p>
        子图的读取点击对应的名称按钮即可，子图的删除点击按钮后的删除符号即可。
      </p>

      <p class="imgContainer">
        <img
          src="../../assets/info_img/db9a283b7681ab61567245b60e876515.png"
          alt=""
        />
      </p>

      <p class="caption">图 29加载和删除子图</p>
    </div>
  </div>
</template>

<script>
export default {}
</script>

<style scoped>
#info {
  line-height: 20px;
  margin-left: 200px;
  margin-right: 200px;
}
#info h1 {
  text-align: left;
}
#info h2 {
  text-align: left;
  font-style: italic;
  margin-left: 25px;
  margin-right: 25px;
}
#info p {
  text-align: left;
  margin-left: 50px;
  margin-right: 50px;
}
#info p.imgContainer {
  text-align: center;
}
#info p.caption {
  text-align: center;
  font-style: italic;
  font-weight: 100;
}
img {
  max-width: 400px;
  max-height: 300px;
}
</style>
