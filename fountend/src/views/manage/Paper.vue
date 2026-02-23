<script>


import index from "vuex";

export default {
  name: "myPaper",
  computed: {
    index() {
      return index
    }
  },
  data() {
    return{
      tableData:[],
      total: 0,
      pageNum: 1,
      pageSize: 5,
      name: "",
      form:{},
      form1: {},
      form2: {},
      type: null,
      dialogFormVisible: false,
      dialogFormVisible1: false,
      dialogFormVisible2: false,
      dialogFormVisible3: false,
      multipleSelection:[],
      questions: [],
      paperQuestions: [],

    }
  },
  created() {//请求分页查询
    this.load()
  },
  methods:{
    filterMethod(query, item) {
      return !query || query == item.type;
    },
    viewPaper(paperId) {
      this.request.get("/paper/view/" + paperId).then(res => {
        this.questions = res.data
        this.dialogFormVisible2 = true
      })
    },
    takePaperByHand(paperId) {
      this.request.get("/question").then(res => {
        this.paperQuestions = res
        console.log(this.paperQuestions)
      })
      this.form2 = {handleQuestionIds: [], paperId: paperId}
      this.dialogFormVisible3 =true
    },
    takePaper(id) {
      this.form1 = {type1:4, type2:4, type3: 2, paperId: id}
      this.dialogFormVisible1 = true
    },
    load(){
      this.request.get("/paper/page",{
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
    generatePaper() {
      this.request.post("/paper/takePaper",this.form1).then(res => {
        if(res.code === '200'){
          this.$message.success("组卷成功")
          this.dialogFormVisible1 = false
        }else{
          this.$message.error(res.msg)

        }
      })
    },
    generatePaperByHand() {
      this.request.post("/paper/takePaperByHand",this.form2).then(res => {
        if(res.code === '200'){
          this.$message.success("组卷成功")
          this.dialogFormVisible3 = false
        }else{
          this.$message.error(res.msg)
        }
      })
    },
    save(){
      this.request.post("/paper",this.form).then(res => {
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
    },

    handleEdit(row){
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogFormVisible =true;
    },
    handleDelete(id){
      this.request.delete("/paper/"+id).then(res => {
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
      this.request.post("paper/del/batch",ids).then(res =>{
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
          style="margin-left: 10px"
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
      <el-table-column prop="name" label="试卷名称" sortable show-overflow-tooltip width="100" align="center">
      </el-table-column>
      <el-table-column prop="score" label="题目总分" align="center">
      </el-table-column>
      <el-table-column prop="duration" label="作答时长(分)" align="center">
      </el-table-column>

      <el-table-column label="试卷" align="center">
        <template v-slot="scope">
          <el-button type="warning" @click="viewPaper(scope.row.id)">查看试卷</el-button>
        </template>
      </el-table-column>

      <el-table-column fixed="right" label="操作" width="350" align="center">
        <template slot-scope="scope">
          <el-button style="color: #3f9dfb" @click="takePaperByHand(scope.row.id)">手动组卷<i class="el-icon-document"></i> </el-button>
          <el-button type="primary" @click="takePaper(scope.row.id)">自动组卷<i class="el-icon-document"></i> </el-button>
          <el-button @click="handleEdit(scope.row)" type="success" >编辑</el-button>
          <el-popconfirm
              class="ml-5"
              confirm-button-text='是'
              cancel-button-text='否'
              icon="el-icon-info"
              icon-color="red"
              title="您确认删除该行数据吗？"
              @confirm="handleDelete(scope.row.id)">
            <el-button type="danger" slot="reference" >删除</el-button>
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

    <el-dialog title="考卷" :visible.sync="dialogFormVisible2" width="60%">
       <el-card>
         <div v-for="(item, index) in questions" :key="item.id" style="margin: 10px 0">
           <div style="margin: 10px 0; font-size: 18px; letter-spacing: 0.5px"><span>{{ index +1 }}.</span> {{ item.name }}
           <span>（{{item.score}}分）</span>
           </div>
           <div v-if="item.type === 1" style="margin: 10px">
             <span style="margin-right: 15px">A. {{item.a}}</span>
             <span style="margin-right: 15px">B. {{item.b}}</span>
             <span style="margin-right: 15px">C. {{item.c}}</span>
             <span style="margin-right: 15px">D. {{item.d}}</span>
           </div>
           <div style="font-size: 12px">
             答案： {{ item.answer }}
           </div>
           <div style="margin: 10px 0;font-size: 12px">
             解析： {{ item.detail }}
           </div>
         </div>
       </el-card>
    </el-dialog>

    <el-dialog title="手动组卷（题型：1选择，2判断，3问答）" :visible.sync="dialogFormVisible3" width="55%">
      <el-form>
        <el-form-item label="">
          <el-transfer :titles="['题库','已选题目']"
                       filter-placeholder="请输入题型" filterable :filter-method="filterMethod"
                       v-model="form2.handleQuestionIds" :props="{ key: 'id', label: 'name'}" :data="paperQuestions"></el-transfer>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible3 = false">取 消</el-button>
        <el-button type="primary" @click="generatePaperByHand">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="自动组卷" :visible.sync="dialogFormVisible1" width="30%">
      <el-form>
        <el-form-item label="选择题数量">
          <el-input v-model="form1.type1" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="判断题数量">
          <el-input v-model="form1.type2" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="问答题数量">
          <el-input v-model="form1.type3" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible1 = false">取 消</el-button>
        <el-button type="primary" @click="generatePaper">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="信息" :visible.sync="dialogFormVisible" width="40%">
      <el-form label-width="100px" size="middle">
        <el-form-item label="测试名称">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="题目总分">
          <el-input v-model="form.score" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="作答时长(分)">
          <el-input v-model="form.duration" autocomplete="off"></el-input>
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