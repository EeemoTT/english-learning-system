<script>
import * as echarts from 'echarts'

let pieCourseOptions = {
  title: {
    text: '', // 主标题
    subtext: '', // 副标题
    left: 'center'
  },
  tooltip: {
    trigger: 'item',
    formatter: '{a} <br/>{b} : {c} ({d}%)'
  },
  legend: {
    orient: 'vertical',
    left: 'left'
  },
  series: [
    {
      name: '', // 鼠标移上去显示内容
      type: 'pie',
      radius: '50%',
      center: ['50%', '60%'],

    }
  ]
}
let pieQuestionOptions = {
  title: {
    text: '', // 主标题
    subtext: '', // 副标题
    left: 'center'
  },
  tooltip: {
    trigger: 'item',
    formatter: '{a} <br/>{b} : {c} ({d}%)'
  },
  legend: {
    orient: 'vertical',
    left: 'left'
  },
  series: [
    {
      name: '', // 鼠标移上去显示内容
      type: 'pie',
      radius: '50%',
      center: ['50%', '60%'],

    }
  ]
}
export default {
  name: "myHome",
  data() {
    return {
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      notices: [],
      datalist: [],

    }
  },
  created() {
    this.request.get('/notice').then(res => {
      this.notices = res || [];
    })
    this.loadData();
    this.loadCourseOption();

  },

  methods: {
    loadData() {
      this.request.get('/statistics/getData').then(res => {
        if (res) {
          this.datalist = res.data
        }
      })
    },
    loadCourseOption() {
      this.request.get('/statistics/getCoursePie').then(res => {
        if (res.code === '200') {
          let chartDom = document.getElementById('coursePie');
          let myChart = echarts.init(chartDom);
          pieCourseOptions.title.text = res.data.text
          pieCourseOptions.title.subtext = res.data.subText
          pieCourseOptions.series[0].name = res.data.name
          pieCourseOptions.series[0].data = res.data.data
          myChart.setOption(pieCourseOptions);
        } else {
          this.$message.error(res.msg)
        }
      })
    },

  }
}
</script>

<template>

  <div>
    <div style="padding: 15px; background-color: #fff; border-radius: 5px ; box-shadow: 0 2px 5px 0 rgba(0,0,0,0.1)">
      您好，{{ user.nickname }}！欢迎使用本系统
    </div>

    <div style="display: flex; margin: 10px 0">
      <div style="width: 50%;padding: 20px; background-color: #fff; border-radius: 5px ; box-shadow: 0 2px 5px 0 rgba(0,0,0,0.1)">
        <div style="margin-bottom: 30px; font-size: 20px; font-weight: bold">公告列表</div>
        <div>
          <el-timeline reverse slot="reference">
            <el-timeline-item v-for="item in notices" :key="item.id" :timestamp="item.createTime">
              <el-popover
                  placement="right"
                  width="200"
                  trigger="hover"
                  :content="item.content">
                <span slot="reference">{{ item.title }}</span>
              </el-popover>
            </el-timeline-item>
          </el-timeline>
        </div>
      </div>
      <div style="width: 50%; height: 300px" class="card" >
        <div><h1 style="text-align: center; padding-bottom: 30px">平台资源数量统计</h1></div>
        <el-card style="height: 180px">
          <div style="padding-top: 10px;padding-bottom: 10px">
            <h2><span>课程数量:</span><span>{{datalist[0]}}</span></h2>
          </div>
          <div style="padding-bottom: 10px">
            <h2><span>单词数量:</span><span>{{datalist[1]}}</span></h2>
          </div>
          <div style="padding-bottom: 10px">
            <h2><span>题目数量:</span><span>{{datalist[2]}}</span></h2>
          </div>
        </el-card>
      </div>
    </div>
    <div style="display: flex; margin: 10px 0">
      <div style="width: 50%; height: 400px" class="card" id="coursePie"></div>
      <div></div>
    </div>
  </div>
</template>

<style>

</style>