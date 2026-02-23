<script>


import axios from "axios";

export default {
  name: "frontCommunity",

  data() {
    return{
      tableData:[],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      name: '',
      form:{},
      dialogFormVisible: false,
      multipleSelection:[],
      editorVisible:false,
      content:'',
      user:JSON.parse(localStorage.getItem('user') || '{}'),
      username: '',
    }
  },
  created() {//请求分页查询
    this.load()
  },
  methods:{
    // 绑定@imgAdd event
    imgAdd(pos, $file) {
      let $vm = this.$refs.md
      // 第一步.将图片上传到服务器.
      const formData = new FormData();
      formData.append('file', $file);
      axios({
        url: 'http://localhost:9090/files/upload',
        method: 'post',
        data: formData,
        headers: {'Content-Type': 'multipart/form-data'},
      }).then((res) => {
        // 第二步.将返回的url替换到文本原位置![...](./0) -> ![...](url)
        $vm.$img2Url(pos, res.data.data);
        console.log(res.data.data);
      })
    },
    load(){
      this.request.get("/community/page",{
        params: {
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          name:this.name,
        }
      }).then(res =>{
        console.log(res)

        this.tableData = res.records
        this.total = res.total
      })
    },

    save(){
      this.request.post("/community",this.form).then(res => {
        if(res){
          this.$message.success("保存成功")
          this.dialogFormVisible = false
          this.load()
        }else{
          this.$message.error("保存失败")
        }
      })
    },

    reset(){
      this.name=""
    },


    handleAdd(){
      this.dialogFormVisible = true
      this.form = {}
      console.log(this.form)

    },

    handleEdit(row){
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogFormVisible =true;
    },
    handleDelete(id){
      this.request.delete("/community/"+id).then(res => {
        if(res){
          this.$message.success("删除成功")
          this.dialogFormVisible = false
          this.load()
        }else{
          this.$message.error("删除失败")
        }
      })
    },

    handleSelectionChange(val){
      this.multipleSelection = val
    },
    delBatch(){
      let ids = this.multipleSelection.map(v => v.id) //[{},{},{}] =>[1,2,3]
      this.request.post("community/del/batch",ids).then(res =>{
        if(res){
          this.$message.success("批量删除成功")
          this.dialogFormVisible = false
          this.load()
        }else{
          this.$message.error("批量删除失败")
        }
      })
    },

    handleSizeChange(pageSize) {
      console.log(pageSize);
      this.pageSize=pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      console.log(pageNum);
      this.pageNum=pageNum
      this.load()
    },
    viewDataInit(content){
      this.editorVisible = true
      this.content = content
    }
  }
}
</script>

<template>
  <div>


    <div style="margin-bottom: 20px">
      <el-input placeholder="请输入名称" style="width: 200px" size="mini" v-model="name"></el-input>
      <el-button type="primary" plain style="margin-left: 10px" size="mini" @click="load(1)">查询</el-button>
      <el-button type="warning" plain style="margin-left: 10px" size="mini" @click="reset">重置</el-button>
    </div>

    <div style="margin: 10px 0">
      <el-button type="primary" @click="handleAdd">创建主题贴</el-button>
      <el-button type="warning" @click="$router.push('/front/communityChange')">管理我的帖子</el-button>
    </div>


    <div style="margin: 10px 0">
      <div style="padding: 10px 0; border-bottom: 1px dashed #ccc" v-for="item in tableData" :key="item.id">
        <div class="pd-10" style="font-size: 20px; color: #32548f ;cursor: pointer" @click="$router.push('/front/communityDetail?id='+ item.id)">{{item.name}}</div>
        <div style="font-size: 12px">
          <i class="el-icon-user-solid"></i> <span>{{item.username}}</span>
          <i class="el-icon-time" style="margin-left: 15px"></i> <span>发布时间：{{item.createTime}}</span>
        </div>
      </div>
    </div>


    <div style="padding: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[5, 10]"
          :page-size="pageSize"
          layout="total, prev, pager, next"
          :total="total">
      </el-pagination>
    </div>

    <el-dialog title="文章信息" :visible.sync="dialogFormVisible" width="70%">
      <el-form label-width="80px" size="middle" >
        <el-form-item label="文章标题" >
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="文章内容">
          <mavon-editor ref="md" v-model="form.content" :ishljs="true" @imgAdd="imgAdd"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog :v-if="editorVisible" :visible.sync="editorVisible" width="50%" :close-on-click-modal="false" destroy-on-close >
      <el-card>
        <!--预览-->
        <mavon-editor
            class="md"
            :value="content"
            :subfield="false"
            :defaultOpen="'preview'"
            :toolbarsFlag="false"
            :editable="false"
            :scrollStyle="true"
            :ishljs="true"
        />
      </el-card>
    </el-dialog>

  </div>
</template>

<style scoped>
.headerBg{
  background-color: #eee !important;
}
</style>