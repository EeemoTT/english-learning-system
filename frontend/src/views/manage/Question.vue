<script>


export default {
  name: "myQuestion",
  data() {
    return{
      tableData:[],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      name: "",
      type: null,
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
      this.request.get("/question/page",{
        params: {
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          name:this.name,
          type:this.type,
        }
      }).then(res =>{
        console.log(res)

        this.tableData = res.records
        this.total = res.total
      })
    },

    save(){
      this.request.post("/question",this.form).then(res => {
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
      this.request.delete("/question/"+id).then(res => {
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
      this.request.post("question/del/batch",ids).then(res =>{
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
      <el-select clearable v-model="type" placeholder="请选择题目类型" style="width: 200px" class="ml-5">
        <el-option v-for="item in [{name:'选择题', value: 1},{name:'判断题', value: 2},{name:'问答题', value: 3}]"
                   :key="item.value" :label="item.name" :value="item.value"></el-option>
      </el-select>
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
      <el-table-column prop="name" label="题目名称" sortable show-overflow-tooltip width="100" align="center">
      </el-table-column>
      <el-table-column prop="type" label="类型"  align="center">
        <template v-slot="scope">
          <span v-if="scope.row.type === 1">选择题</span>
          <span v-if="scope.row.type === 2">判断题</span>
          <span v-if="scope.row.type === 3">问答题</span>
        </template>
      </el-table-column>
      <el-table-column prop="a" label="a选项" show-overflow-tooltip align="center">
      </el-table-column>
      <el-table-column prop="b" label="b选项" show-overflow-tooltip align="center">
      </el-table-column>
      <el-table-column prop="c" label="c选项" show-overflow-tooltip align="center">
      </el-table-column>
      <el-table-column prop="d" label="d选项" show-overflow-tooltip align="center">
      </el-table-column>
      <el-table-column prop="score" label="分数" align="center">
      </el-table-column>
      <el-table-column prop="answer" label="答案" show-overflow-tooltip align="center">
      </el-table-column>
      <el-table-column prop="detail" label="解析" show-overflow-tooltip align="center">
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
          :page-sizes="[10, 20 , 50]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>

    <el-dialog title="信息" :visible.sync="dialogFormVisible" width="40%">
      <el-form label-width="80px" size="middle">
        <el-form-item label="类型">
          <el-select clearable v-model="form.type" placeholder="请选择" style="width: 100%">
            <el-option v-for="item in [{name:'选择题', value:1},{name:'判断题', value:2},{name:'问答题', value:3}]"
                       :key="item.value" :label="item.name" :value="item.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="名称">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="A选项" v-if="form.type === 1">
          <el-input v-model="form.a" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="B选项" v-if="form.type === 1">
          <el-input v-model="form.b" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="C选项" v-if="form.type === 1">
          <el-input v-model="form.c" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="D选项" v-if="form.type === 1">
          <el-input v-model="form.d" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="分数">
          <el-input v-model="form.score" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="答案" v-if="form.type === 1">
          <el-radio v-model="form.answer" label="A">A</el-radio>
          <el-radio v-model="form.answer" label="B">B</el-radio>
          <el-radio v-model="form.answer" label="C">C</el-radio>
          <el-radio v-model="form.answer" label="D">D</el-radio>
        </el-form-item>
        <el-form-item label="答案" v-if="form.type === 2">
          <el-radio v-model="form.answer" label="TRUE">TRUE</el-radio>
          <el-radio v-model="form.answer" label="FALSE">FALSE</el-radio>
        </el-form-item>
        <el-form-item label="答案" v-if="form.type === 3">
          <el-input type="textarea" v-model="form.answer" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="解析">
          <el-input type="textarea" :rows="5" v-model="form.detail" autocomplete="off"></el-input>
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