<script>


export default {
  name: "myWord",
  data() {
    return{
      tableData:[],
      total: 0,
      pageNum: 1,
      pageSize: 20,
      word: "",
      book: null,
      form:{},
      user: JSON.parse(localStorage.getItem('user') || '{}'),
      dialogFormVisible: false,
      multipleSelection:[],
    }
  },
  created() {//请求分页查询
    this.load()
  },
  methods:{
    load(){
      this.request.get("/word/page",{
        params: {
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          word:this.word,
          book:this.book,
        }
      }).then(res =>{
        console.log(res)

        this.tableData = res.records
        this.total = res.total
      })
    },

    save(){
      this.request.post("/word",this.form).then(res => {
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
      this.word = ""
      this.book = null
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
      this.request.delete("/word/"+id).then(res => {
        if(res){
          this.$message.success("删除成功")
          this.dialogFormVisible = false
          this.load()
        }else{
          this.$message.error("删除失败")
        }
      })
    },

    handleSelectionChange(rows){
      this.multipleSelection = rows.map(v => v.id)
    },
    delBatch(){
      let ids = this.multipleSelection.map(v => v.id) //[{},{},{}] =>[1,2,3]
      this.request.post("word/del/batch",ids).then(res =>{
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
    exp() {
      window.open("http://localhost:9090/word/export")
    },
    handleExcelImportSuccess() {
      this.$message.success("导入成功")
      this.load()
    },

  }
}
</script>

<template>
  <div>


    <div style="padding: 10px 0">
      <el-input style="width: 200px"  placeholder="请输入名称" suffix-icon="el-icon-search" v-model="word"></el-input>
      <el-input style="width: 200px;margin-left: 20px"  placeholder="请输入词书" suffix-icon="el-icon-search" v-model="book"></el-input>
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
      <el-upload action="http://localhost:9090/word/import" :show-file-list="false" accept="xls" :on-success="handleExcelImportSuccess" style="display:inline-block">
        <el-button type="primary" class="ml-5">导入<i class="el-icon-bottom"></i></el-button>
      </el-upload>
      <el-button type="primary" @click="exp" class="ml-5">导出<i class="el-icon-top"></i></el-button>
    </div>

    <el-table
        :data="tableData"
        border
        stripe
        style="width: 100%"
        @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column prop="id" label="ID" sortable width="80" align="center">
      </el-table-column>
      <el-table-column prop="word" label="单词" sortable  align="center">
      </el-table-column>
      <el-table-column prop="ukphone" label="音标"  align="center">
      </el-table-column>
      <el-table-column prop="tran" label="解释" align="center">
      </el-table-column>
      <el-table-column prop="book" label="词书" align="center">
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
          :page-sizes="[20, 50, 100]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>

    <el-dialog title="单词" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="80px" size="middle" >
        <el-form-item label="单词" :label-width="formLabelWidth">
          <el-input v-model="form.word" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="音标" :label-width="formLabelWidth">
          <el-input v-model="form.ukphone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="解释" :label-width="formLabelWidth">
          <el-input v-model="form.tran" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="词书" :label-width="formLabelWidth">
          <el-input v-model="form.book" autocomplete="off"></el-input>
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

</style>