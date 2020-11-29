import Vue from 'vue'
import Router from 'vue-router'
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'login', //登入
      component: () => import('@/components/common/login')
    },
    {
      path: '/index', //老師index
      component: () => import('@/components/admin/index'),
      children: [
        {
          path: '/', 
          component: () => import('@/components/common/hello')
        },
        {
          path:'/grade', //學生成績
          component: () => import('@/components/charts/grade')
        },
        {
          path: '/selectExamToPart', //學生分區
          component: () => import('@/components/teacher/selectExamToPart')
        },
        {
          path: '/scorePart',
          component: () => import('@/components/charts/scorePart')
        },
        {
          path: '/allStudentsGrade', //所有學生統計
          component: () => import('@/components/teacher/allStudentsGrade')
        },
        {
          path: '/selectExam', //查詢所有考試
          component: () => import('@/components/teacher/selectExam')
        },
        {
          path: '/addExam', //新增試卷
          component: () => import('@/components/teacher/addExam')
        },
        {
          path: '/selectAnswer', //查詢問題
          component: () => import('@/components/teacher/selectAnswer')
        },
        {
          path: '/addAnswer', //新增問題
          component: () => import('@/components/teacher/addAnswer')
        },
        {
          path: '/addAnswerChildren', //新增考試
          component: () => import('@/components/teacher/addAnswerChildren')
        },
        {
          path: '/studentManage', //管理學生
          component: () => import('@/components/teacher/studentManage')
        },
        {
          path: '/addStudent', //新增學生
          component: () => import('@/components/teacher/addStudent')
        },
        {
          path: '/teacherManage',
          component: () => import('@/components/admin/tacherManage')
        },
        {
          path: '/addTeacher',
          component: () => import ('@/components/admin/addTeacher')
        }
      ]
    },
    {
      path: '/student',
      component: () => import('@/components/student/index'),
      children: [
        {path:"/",component: ()=> import('@/components/student/myExam')},
        {path:'/startExam', component: () => import('@/components/student/startExam')},
        {path: '/manager', component: () => import('@/components/student/manager')},
        {path: '/examMsg', component: () => import('@/components/student/examMsg')},
        {path: '/studentScore', component: () => import("@/components/student/answerScore")},
        {path: '/scoreTable', component: () => import("@/components/student/scoreTable")}
      ]
    },
    {path: '/answer',component: () => import('@/components/student/answer')}
  ]
})
