<script>
export default {
  name: "myNotice",
  data() {
    return{
      tableData:[],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      title: "",
      form:{},
      dialogFormVisible: false,
      multipleSelection:[],
    }
  },
  created() {//请求分页查询
    this.load()
  },
  methods:{
    load(){
      this.request.get("/notice/page",{
        params: {
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          title:this.title
        }
      }).then(res =>{
        console.log(res)

        this.tableData = res.records
        this.total = res.total
      })
    },

    save(){
      this.request.post("/notice",this.form).then(res => {
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
      this.title=""
    },


    handleAdd(){
      this.dialogFormVisible = true
      this.form = {}
    },

    handleEdit(row){
      this.form = row
      this.dialogFormVisible =true;
    },
    handleDelete(id){
      this.request.delete("/notice/"+id).then(res => {
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
      this.request.post("notice/del/batch",ids).then(res =>{
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
  }
}
</script>

<template>
  <div>


    <div style="padding: 10px 0">
      <el-input style="width: 200px"  placeholder="请输入名称" suffix-icon="el-icon-search" v-model="title"></el-input>
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
      <el-table-column prop="title" label="标题" width="120" align="center">
      </el-table-column>
      <el-table-column prop="content" label="内容" align="center">
      </el-table-column>
      <el-table-column prop="creator" label="创建人" align="center">
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" align="center">
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
          :page-sizes="[5, 10]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>

    <el-dialog title="信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="80px" size="middle" >
        <el-form-item label="标题" :label-width="formLabelWidth">
          <el-input v-model="form.title" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="内容" :label-width="formLabelWidth">
          <el-input v-model="form.content" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="创建人" :label-width="formLabelWidth">
          <el-input v-model="form.creator" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<style scoped>
.headerBg{
  background-color: #eee !important;
}
</style>