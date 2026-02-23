<template>
  <div>


    <div style="padding: 10px 0">
      <el-input style="width: 200px"  placeholder="请输入名称" suffix-icon="el-icon-search" v-model="username"></el-input>
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
      <el-table-column prop="username" label="用户名" sortable width="120" align="center">
      </el-table-column>
      <el-table-column prop="nickname" label="昵称" width="120" align="center">
      </el-table-column>
      <el-table-column prop="email" label="邮箱" align="center">
      </el-table-column>
      <el-table-column prop="phone" label="电话" align="center">
      </el-table-column>
      <el-table-column prop="role" label="用户等级" width="80" :formatter="formatRole" align="center">
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

    <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="80px" size="middle" >
        <el-form-item label="用户名" :label-width="formLabelWidth">
          <el-input v-model="form.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="昵称" :label-width="formLabelWidth">
          <el-input v-model="form.nickname" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" :label-width="formLabelWidth">
          <el-input v-model="form.email" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="电话" :label-width="formLabelWidth">
          <el-input v-model="form.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="用户等级" :label-width="formLabelWidth">
          <el-input v-model="form.role" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "myAdmin",
  data() {
    return{
      tableData:[],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      username: "",
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
      this.request.get("/admin/page",{
        params: {
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          username:this.username
        }
      }).then(res =>{
        console.log(res)

        this.tableData = res.records
        this.total = res.total
      })
    },

    save(){
      this.request.post("/admin",this.form).then(res => {
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
      this.username=""
    },

    formatRole:function(row){
      return row.role == 'ADMIN' ? "管理员" :row.role == null ? "管理员": "aaa"
    },

    handleAdd(){
      this.dialogFormVisible = true
      this.form = {}
    },

    handleEdit(row){
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogFormVisible =true;
    },
    handleDelete(id){
      this.request.delete("/admin/"+id).then(res => {
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
      this.request.post("admin/del/batch",ids).then(res =>{
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



<style>
.headerBg{
  background-color: #eee !important;
}
</style>