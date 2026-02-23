import Vue from 'vue'
import VueRouter from 'vue-router'
import store from "@/store";

Vue.use(VueRouter)

const routes = [
  {
    path: '/manage/',
    name: 'Manage',
    component: () => import('../views/Manage.vue'),
    redirect:"/manage/home",
    children: [
      {path: 'home', name: '首页', component: ()=> import('../views/manage/ManageHome.vue')},
      {path: 'user', name: '用户管理', component: ()=> import('../views/manage/User.vue')},
      {path: 'admin', name: '管理员信息', component: ()=> import('../views/manage/Admin.vue')},
      {path: 'person', name: '个人信息', component: ()=> import('../views/manage/AdminPerson.vue')},
      {path: 'notice', name: '公告信息', component: ()=> import('../views/manage/Notice.vue')},
      {path: 'course', name: '课程信息', component: ()=> import('../views/manage/Course.vue')},
      {path: 'community', name: '评论信息', component: ()=> import('../views/manage/Community.vue')},
      {path: 'question', name: '题目信息', component: ()=> import('../views/manage/Question.vue')},
      {path: 'paper', name: '试卷信息', component: ()=> import('../views/manage/Paper.vue')},
      {path: 'test', name: '考试信息', component: ()=> import('../views/manage/Test.vue')},
      {path: 'word', name: '单词信息', component: ()=> import('../views/manage/Word.vue')},


    ]
  },
  {
    path: '/Front/',
    name: 'Manage',
    component: () => import('../views/Front.vue'),
    redirect:"/Front/home",
    children: [
      {path: 'home', name: '首页', component: ()=> import('../views/front/FrontHome.vue')},
      {path: 'person', name: '个人信息', component: ()=> import('../views/front/UserPerson.vue')},
      {path: 'course', name: '课程信息', component: () => import('../views/front/Course.vue')},
      {path: 'courseDetail', name: '课程详情', component: () => import('../views/front/CourseDetail.vue')},
      {path: 'community', name: '评论信息', component: ()=> import('../views/front/Community.vue')},
      {path: 'InteractiveStudy', name: '互动式学习', component: ()=> import('../views/front/InteractiveStudy.vue')},
      {path: 'communityDetail', name: '主题贴', component: ()=> import('../views/front/CommunityDetail.vue')},
      {path: 'personCenter', name: '个人中心', component: ()=> import('../views/front/PersonCenter.vue')},
      {path: 'communityChange', name: '修改主题贴', component: ()=> import('../views/front/CommunityChange.vue')},
      {path: 'test', name: '题目练习', component: ()=> import('../views/front/Test.vue')},
      {path: 'paper', name: '考试页面', component: ()=> import('../views/front/Paper.vue')},
      {path: 'personalScore', name: '考试成绩', component: ()=> import('../views/front/PersonalScore.vue')},
      {path: 'scoreAnalysis', name: '成绩分析', component: ()=> import('../views/front/ScoreAnalysis.vue')},
      {path: 'personalFavor', name: '收藏课程', component: ()=> import('../views/front/PersonalFavor.vue')},
      {path: 'wordStation', name: '单词学习', component: ()=> import('../views/front/Word.vue')},
      {path: 'bookChoosing', name: '学习目标', component: ()=> import('../views/front/BookChoosing.vue')},
      {path: 'learning', name: '单词学习', component: ()=> import('../views/front/Learning.vue')},
      {path: 'wordReport', name: '单词学习报告', component: ()=> import('../views/front/WordReport.vue')},
      {path: 'onlineAnswer', name: '单词学习报告', component: ()=> import('../views/front/OnlineAnswer.vue')},




    ]
  },
  {
    path: '/about',
    name: 'about',
    component: () => import('../views/AboutView.vue')
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/register',
    name: 'register',
    component: () => import('../views/Register.vue')
  },
  {
    path: '*',
    name: 'NotFound',
    component: () => import('../views/404.vue')
  }
]

const router = new VueRouter({
  mode:'history',
  base: process.env.BASE_URL,
  routes
})

//路由守卫
router.beforeEach((to, from , next) => {
  localStorage.setItem("currentPathName", to.name) //设置路由名
  store.commit("setPath") //store数据更新
  next()//放行
})

export default router
