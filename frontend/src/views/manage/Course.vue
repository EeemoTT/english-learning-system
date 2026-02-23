<script>
import E from 'wangeditor'
export default {
  name: "myCourse",
  data() {
    return{
      editor: null,
      tableData:[],
      viewData: null,
      total: 0,
      pageNum: 1,
      pageSize: 10,
      name: "",
      form:{},
      dialogFormVisible: false,
      editorVisible:false,
      multipleSelection:[],
      user: JSON.parse(localStorage.getItem('user') || '{}'),
    }
  },
  created() {//请求分页查询
    this.load()
  },
  methods:{
    initWangEditor(content) {
      this.$nextTick(() => {
        this.editor = new E('#editor')
        this.editor.config.placeholder = '请输入内容'
        this.editor.config.uploadFileName = 'file'
        this.editor.config.uploadImgServer = 'http://localhost:9090/files/wang/upload'
        this.editor.create()
        setTimeout(() => {
          this.editor.txt.html(content)
        })
      })
    },
    load(){
      this.request.get("/course/page",{
        params: {
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          name:this.name
        }
      }).then(res =>{
        console.log(res)

        this.tableData = res.records
        this.total = res.total
      })
    },

    save(){
      this.form.introduction = this.editor.txt.html()
      this.request.post("/course",this.form).then(res => {
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
      this.name= ""
    },


    handleAdd(){
      this.dialogFormVisible = true
      this.form = {}
      this.initWangEditor('')
    },

    handleEdit(row){
      this.form = row
      this.dialogFormVisible =true;
      this.initWangEditor(this.form.introduction || '')
    },
    handleDelete(id){
      this.request.delete("/course/"+id).then(res => {
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
      this.request.post("course/del/batch",ids).then(res =>{
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
    handleImgSuccess(res){
      this.form.img = res.data
    },
    handleVideoSuccess(res){
      this.form.video = res.data
    },
    download(url){
      location.href =url
    },
    viewDataInit(data){
      this.editorVisible = true
      this.viewData = data


    }
  }
}
</script>

<template>
  <div>
    <div style="padding: 10px 0">
      <el-input style="width: 200px"  placeholder="请输入名称" suffix-icon="el-icon-search" v-model="name"></el-input>
      <el-button class="ml-5" type="primary" @click = "load">搜索</el-button>
      <el-button type="warning" @click = "reset">重置</el-button>
    </div>

    <div style="margin: 10px 0">
      <el-button type="primary" @click="handleAdd">新建</el-button>
      <el-popconfirm
          class="ml-5"
          confirm-button-text='是'
          cancel-button-text='否'
          icon="el-icon-info"
          icon-color="red"
          title="您确认删除吗？"
          @confirm="delBatch">
        <el-button type="danger" slot="reference">批量删除<i class="el-icon-delete"></i></el-button>
      </el-popconfirm>
    </div>

    <el-table
        :data="tableData"
        border
        stripe
        style="width: 100%"
        :default-sort = "{prop: 'date', order: 'descending'}"
        header-cell-class-name="'headerBg'"
        @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column prop="id" label="ID" sortable width="80" align="center">
      </el-table-column>
      <el-table-column label="课程封面">
        <template v-slot="scope">
          <div style="display: flex; align-items: center">
            <el-image style="width: 60px; height: 40px"
                      lazy
                      v-if="scope.row.img"
                      :src="scope.row.img"
                      :preview-src-list="[scope.row.img]"
            ></el-image>
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="name" label="课程名称" width="120" align="center">
      </el-table-column>
      <el-table-column prop="type" label="课程类型" align="center">
      </el-table-column>
      <el-table-column prop="file" label="课程介绍" align="center" show-overflow-tooltip>
        <template v-slot="scope">
          <el-button type="primary" size="mini" @click="viewDataInit(scope.row.file)">点击查看</el-button>
        </template>
      </el-table-column>
      <el-table-column prop="video" label="课程视频" align="center" show-overflow-tooltip>
        <template v-slot="scope">
          <el-button type="warning" size="mini" @click="download(scope.row.video)" v-if="scope.row.type === 'VIDEO'">点击下载</el-button>
        </template>
      </el-table-column>
      <el-table-column prop="introduction" label="课程内容" align="center" show-overflow-tooltip>
        <template v-slot="scope">
          <el-button type="success" size="mini" @click="viewDataInit(scope.row.introduction)">点击查看</el-button>
        </template>
      </el-table-column>
      <el-table-column prop="recommend" label="是否推荐" align="center">
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="150" align="center">
        <template slot-scope="scope">
          <el-button @click="handleEdit(scope.row)" type="primary" size="small">编辑</el-button>
          <el-popconfirm
              class="ml-5"
              confirm-button-text='是'
              cancel-button-text='否'
              icon="el-icon-info"
              icon-color="red"
              title="您确认删除该行数据吗？"
              @confirm="handleDelete(scope.row.id)">
            <el-button type="danger" slot="reference" size="small">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <div style="padding: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[10,20,50]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>

    <el-dialog title="课程信息" :visible.sync="dialogFormVisible" destroy-on-close width="50%">
      <el-form label-width="100px" size="middle" >
        <el-form-item label="课程封面">
          <el-upload
              :action="'http://localhost:9090/files/upload'"
              :headers="{ token: user.token }"
              list-type="picture"
              :on-success="handleImgSuccess"
          >
            <el-button type="primary">上传图片</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item prop="name" label="课程名称">
          <el-input v-model="form.name" autocomplete="off" placeholder="请输入课程名称"></el-input>
        </el-form-item>
        <el-form-item prop="file" label="课程介绍">
          <el-input type="textarea" :rows="5" v-model="form.file" autocomplete="off" placeholder="请输入课程介绍"></el-input>
        </el-form-item>
        <el-form-item prop="type" label="课程类型">
          <el-select v-model="form.type" placeholder="请选择类型" style="width: 100%">
            <el-option label="视频课程" value="VIDEO"></el-option>
            <el-option label="图文课程" value="TEXT"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="recommend" label="是否推荐">
          <div>（新课程默认不推荐，只有一个推荐位！！！）</div>
        <el-select v-model="form.recommend" placeholder="否" style="width: 100%">
          <el-option label="是" value="是"></el-option>
          <el-option label="否" value="否"></el-option>
        </el-select>
        </el-form-item>
        <el-form-item label="课程视频">
          <el-upload
              :action="'http://localhost:9090/files/upload'"
              :headers="{ token: user.token }"
              :on-success="handleVideoSuccess"
          >
            <el-button type="primary">上传视频（视频课程）</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item prop="introduction" label="课程内容">
          <div id="editor"></div>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog :v-if="editorVisible" :visible.sync="editorVisible" width="50%" :close-on-click-modal="false" destroy-on-close >
      <div v-html="viewData" class="w-e-text w-e-text-container"></div>
    </el-dialog>

  </div>
</template>

<style scoped>
.headerBg{
  background-color: #eee !important;
}
</style>