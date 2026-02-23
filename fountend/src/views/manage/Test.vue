<script>

export default {
  name: "myTest",
  data() {
    return{
      tableData:[],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      name: "",
      form:{},
      form1:{},
      dialogFormVisible: false,
      dialogFormVisible1: false,
      multipleSelection:[],
      papers:[]
    }
  },
  created() {//请求分页查询
    this.load()
  },
  methods:{
    setPaper(testId) {
      this.form1 ={testId: testId}
      this.request.get("testPaper/test/" + testId).then(res => {
        if(res.data) {
          this.form1.paperId = res.data.paperId

        }
        this.dialogFormVisible1 = true
      })

    },
    load(){
      this.request.get("/test/page",{
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
      this.request.get("/paper").then(res =>{
        this.papers = res
        console.log(this.papers)
      })
    },

    save1(){
      this.request.post("/testPaper",this.form1).then(res => {
        if(res.code === '200'){
          this.$message.success("设置成功")
          this.dialogFormVisible1 = false

        }else{
          this.$message.error("设置失败")
        }
      })
    },

    save(){
      this.request.post("/test",this.form).then(res => {
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
      this.type= null
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
      this.request.delete("/test/"+id).then(res => {
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
      this.request.post("test/del/batch",ids).then(res =>{
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
      <el-input style="width: 200px"  placeholder="请输入名称" suffix-icon="el-icon-search" v-model="name"></el-input>
      <el-button class="ml-5" type="primary" @click = "load">搜索</el-button>
      <el-button type="warning" @click = "reset">重置</el-button>
    </div>

    <div style="margin: 10px 0">
      <el-button type="primary" @click="handleAdd">新增</el-button>
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
      <el-table-column prop="name" label="考试名称" sortable show-overflow-tooltip align="center">
      </el-table-column>
      <el-table-column prop="state" label="考试状态" show-overflow-tooltip align="center">
      </el-table-column>
      <el-table-column label="设置考卷" align="center">
        <template v-slot="scope">
          <el-button type="primary" @click="setPaper(scope.row.id)">设置</el-button>
        </template>
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
          :page-sizes="[5, 10 , 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>

    <el-dialog title="考试信息" :visible.sync="dialogFormVisible1" width="40%">
      <el-form label-width="80px" size="middle">
        <el-form-item label="试卷">
            <el-select clearable v-model="form1.paperId" placeholder="请选择试卷" style="width: 90%">
              <el-option v-for="item in papers" :key="item.id" :label="item.name" :value="item.id"></el-option>
            </el-select>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible1 = false">取 消</el-button>
        <el-button type="primary" @click="save1">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="考试信息" :visible.sync="dialogFormVisible" width="40%">
      <el-form label-width="80px" size="middle">
        <el-form-item label="考试名称">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="考试状态">
          <el-select clearable v-model="form.state" placeholder="请选择" style="width: 100%">
            <el-option v-for="item in [{name:'未开始', value:'未开始'},{name:'进行中', value:'进行中'},{name:'已结束', value:'已结束'}]"
                       :key="item.value" :label="item.name" :value="item.value"></el-option>
          </el-select>
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