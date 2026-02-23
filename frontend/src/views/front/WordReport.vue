<script>
import * as echarts from 'echarts'

let pieKnownOptions = {
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

let lineSevenDayOptions = {
  title: {
    text: '', // 主标题
    subtext: '', // 副标题
    left: 'center'
  },
  xAxis: {
    type: 'category',
    data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'] // 示例数据：统计的维度（横坐标）
  },
  yAxis: {
    type: 'value'
  },
  tooltip: {
    trigger: 'item'
  },
  series: [
    {
      data: [120, 200, 150, 80, 70, 110, 130], // 示例数据：横坐标维度对应的值（纵坐标）
      type: 'line',
    }
  ]
}
export default {
  name: "WordReport",
  data() {
    return {
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      book: "",
      bookWords: null,
      studiedWords: null,
      reviewWords:null,

    }
  },
  created() {
    this.loadBook();
    this.loadStudiedWords();
    this.loadReviewCount();
    this.loadKnownOption();
    this.loadSevenDay();
  },
  methods: {
    loadBook() {
      this.request.get('/statisticsForWord/book' ,{
        params: {
          userId: this.user.id
        }
      }).then(res => {
        if (res.code === '200') {
          this.book = res.data.book
          this.loadBookWords();
        }
      })
    },

    loadBookWords() {
      this.request.get('/statisticsForWord/wordCount',{
        params: {
          book: this.book
        }
      }).then(res => {
        if (res.code === '200') {
          this.bookWords = res.data

        }
      })
    },
    loadStudiedWords(){
      this.request.get('/statisticsForWord/learnedCount',{
        params: {
          userId: this.user.id
        }
      }).then(res => {
        if (res.code === '200') {
          this.studiedWords = res.data

        }
      })
    },
    loadReviewCount(){
      this.request.get('/statisticsForWord/reviewCount',{
        params: {
          userId: this.user.id
        }
      }).then(res => {
        if (res.code === '200') {
          this.reviewWords = res.data
        }
      })
    },
    loadKnownOption() {
      this.request.get('/statisticsForWord/getPhasePie',
          {
            params: {
              userId: this.user.id
            }
          }
      ).then(res => {
        if (res.code === '200') {
          let chartDom = document.getElementById('knownPie');
          let myChart = echarts.init(chartDom);
          pieKnownOptions.title.text = res.data.text
          pieKnownOptions.title.subtext = res.data.subText
          pieKnownOptions.series[0].name = res.data.name
          pieKnownOptions.series[0].data = res.data.data
          myChart.setOption(pieKnownOptions);
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    loadSevenDay() {
      this.request.get('/statisticsForWord/getStudyTime7',
      {
        params: {
          userId: this.user.id
        }
      }
      ).then(res => {
        if (res.code === '200') {
          let chartDom = document.getElementById('sevenDayLine');
          let myChart = echarts.init(chartDom);
          lineSevenDayOptions.title.text = res.data.text
          lineSevenDayOptions.title.subtext = res.data.subText
          lineSevenDayOptions.xAxis.data = res.data.xAxis
          lineSevenDayOptions.series[0].data = res.data.yAxis
          myChart.setOption(lineSevenDayOptions);
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    back() {
      this.$router.push("/front/wordStation")
    }


  },
}
</script>

<template>
 <div>
   <div>
     <el-button size="small" style="color: #3f9dfb;border-radius: 5px ; box-shadow: 0 2px 5px 0 rgba(0,0,0,0.1);text-align: center" @click="back"><i class="el-icon-arrow-left"></i>返回</el-button>
   </div>
   <div class="main-content" style="width: 90%;">
     <div style="padding: 15px; color: #3f9dfb; border-radius: 5px ; box-shadow: 0 2px 5px 0 rgba(0,0,0,0.1);text-align: center">
       您好，{{ user.nickname }}！请查看您的学习报告
     </div>
     <div style="display: flex; margin: 10px 0;">
       <div style="width: 50%; height: 300px" class="card" >
         <div style="font-size: 20px;color: #3f9dfb;margin-bottom: 45px">
           您目前正在学习的词书：
         </div>
         <div style="text-align: center;font-size: 30px;color: #f6a50e" v-if="this.book&&this.book.length">
           <i class="el-icon-collection"></i>{{this.book}}
         </div>
         <div style="margin-top: 80px">
           <span style="color: #ff4316">今日需要复习单词：{{this.reviewWords}}</span>
         </div>
       </div>
       <div style="width: 50%; height: 300px" class="card">
         <div style="font-size: 20px;color: #3f9dfb;margin-bottom: 45px">
           学习进度:
         </div>
         <div style="text-align: center;font-size: 30px;color: #f6a50e">
           <i class="el-icon-loading"></i>{{this.studiedWords}}/{{this.bookWords}}
         </div>
         <div style="margin-top: 80px;font-size: 18px;color: #3f9dfb">
           Keep on going and never give up !!!
         </div>
         <div style="margin-top: 10px;font-size: 18px;color: #3f9dfb">
           Try practising everyday!!!
         </div>
       </div>
     </div>
     <div style="display: flex; margin: 10px 0;">
       <div style="width: 50%; height: 400px" class="card" id="knownPie">
         您还未开始学习！！！
       </div>
       <div style="width: 50%; height: 400px" class="card" id="sevenDayLine">
         您还未开始学习！！！
       </div>
     </div>
   </div>
 </div>
</template>

<style scoped>
.main-content {
  width: 100%;
  margin: 5px auto;
}
.card {
  padding: 20px;
  background-color: #fff;
  border-radius: 5px;
  box-shadow: 0 2px 5px 0 rgba(0, 0, 0, 0.1);
}

</style>