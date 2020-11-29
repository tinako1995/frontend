<!--學生答題-->
<template>
  <div id="answer">
    <!--top Menu-->
     <div class="top">
       <ul class="item">
         <li><i class="iconfont icon-menufold icon20" ref="toggle" @click="slider_flag = !slider_flag"></i></li>
         <li>{{examData.type}}-{{examData.source}}</li>
         <li @click="flag = !flag">
           <i class="iconfont icon-user icon20"></i>
           <div class="msg"  v-if="flag" @click="flag = !flag">
             <p>姓名：{{userInfo.name}}</p>
             <p>學號:  {{userInfo.id}}</p>
           </div>
         </li>
         <li><i class="iconfont icon-arrLeft icon20"></i></li>
       </ul>
     </div>
     <div class="flexarea">
        <!--左边题目编号区-->
        <transition name="slider-fade">
          <div class="left" v-if="slider_flag">
            <ul class="l-top">
              <li>
                <a href="javascript:;"></a>
                <span>本題</span>
              </li>
              <li>
                <a href="javascript:;"></a>
                <span>未作答</span>
              </li>
              <li>
                <a href="javascript:;"></a>
                <span>已作答</span>
              </li>
              <li>
                <a href="javascript:;"></a>
                <span>標記</span>
              </li>
            </ul>
            <div class="l-bottom">
              <div class="item">
                <p>選擇題</p>
                <ul>
                  <li v-for="(list, index1) in topic[1]" :key="index1">
                    <a href="javascript:;" 
                      @click="change(index1)"
                      :class="{'border': index == index1 && currentType == 1,'bg': bg_flag && topic[1][index1].isClick == true}">
                      <span :class="{'mark': topic[1][index1].isMark == true}"></span>
                      {{index1+1}}
                    </a>
                  </li>
                </ul>
              </div>
              <div class="item">
                <p>填空題</p>
                <ul>
                  <li v-for="(list, index2) in topic[2]" :key="index2">
                    <a href="javascript:;" @click="fill(index2)" :class="{'border': index == index2 && currentType == 2,'bg': fillAnswer[index2][3] == true}"><span :class="{'mark': topic[2][index2].isMark == true}"></span>{{topicCount[0]+index2+1}}</a>
                  </li>
                </ul>
              </div>
              <div class="item">
                <p>是非題部分</p>
                <ul>
                  <li v-for="(list, index3) in topic[3]" :key="index3">
                    <a href="javascript:;" @click="judge(index3)" :class="{'border': index == index3 && currentType == 3,'bg': bg_flag && topic[3][index3].isClick == true}"><span :class="{'mark': topic[3][index3].isMark == true}"></span>{{topicCount[0]+topicCount[1]+index3+1}}</a>
                  </li>
                </ul>
              </div>
              <div class="final" @click="commit()">交卷</div>
            </div>
          </div>
        </transition>  
        <!--答題區-->
        <transition name="slider-fade">
        <div class="right">
          <div class="title">
            <p>{{title}}</p>
            <i class="iconfont icon-right auto-right"></i>
            <span>全卷共{{topicCount[0] + topicCount[1] + topicCount[2]}}題 <i class="iconfont icon-time"></i>倒计时：<b>{{time}}</b>分钟</span>
          </div>
          <div class="content">
            <p class="topic"><span class="number">{{number}}</span>{{showQuestion}}</p>
            <div v-if="currentType == 1">
              <el-radio-group v-model="radio[index]" @change="getChangeLabel" >
                <el-radio :label="1">{{showAnswer.answerA}}</el-radio>
                <el-radio :label="2">{{showAnswer.answerB}}</el-radio>
                <el-radio :label="3">{{showAnswer.answerC}}</el-radio>
                <el-radio :label="4">{{showAnswer.answerD}}</el-radio>
              </el-radio-group>
              <div class="analysis" v-if="isPractice">
                <ul>
                  <li> <el-tag type="success">正確答案：</el-tag><span class="right">{{reduceAnswer.rightAnswer}}</span></li>
                  <li><el-tag>題目解析：</el-tag></li>
                  <li>{{reduceAnswer.analysis == null ? '暫無解析': reduceAnswer.analysis}}</li>
                </ul>
              </div>
            </div>
            <div class="fill" v-if="currentType == 2">
              <div v-for="(item,currentIndex) in part" :key="currentIndex">
                <el-input placeholder="請作答"
                  v-model="fillAnswer[index][currentIndex]"
                  clearable
                  @blur="fillBG">
                </el-input>
              </div>
              <div class="analysis" v-if="isPractice">
                <ul>
                  <li> <el-tag type="success">正確答案：</el-tag><span class="right">{{topic[2][index].answer}}</span></li>
                  <li><el-tag>題目解析：</el-tag></li>
                  <li>{{topic[2][index].analysis == null ? '暫無解析': topic[2][index].analysis}}</li>
                </ul>
              </div>
            </div>
            <div class="judge" v-if="currentType == 3">
              <el-radio-group v-model="judgeAnswer[index]" @change="getJudgeLabel" v-if="currentType == 3">
                <el-radio :label="1">是</el-radio>
                <el-radio :label="2">否</el-radio>
              </el-radio-group>
              <div class="analysis" v-if="isPractice">
                <ul>
                  <li> <el-tag type="success">正確答案：</el-tag><span class="right">{{topic[3][index].answer}}</span></li>
                  <li><el-tag>題目解析：</el-tag></li>
                  <li>{{topic[3][index].analysis == null ? '暫無解析': topic[3][index].analysis}}</li>
                </ul>
              </div>
            </div>
          </div>
          <div class="operation">
            <ul class="end">
              <li @click="previous()"><i class="iconfont icon-previous"></i><span>上一題</span></li>
              <li @click="mark()"><i class="iconfont icon-mark-o"></i><span>標記</span></li>
              <li @click="next()"><span>下一題</span><i class="iconfont icon-next"></i></li>
            </ul>
          </div>
        </div>
        </transition>
     </div> 
  </div>
</template>

<script>
import store from '@/vuex/store'
import {mapState} from 'vuex'
export default {
  store,
  data() {
    return {
      startTime: null, //考試開始時間
      endTime: null, //考試結束時間
      time: null, //考試時間
      reduceAnswer:[],  //答案
      answerScore: 0, //總分
      bg_flag: false, //作答後變更背景色
      isFillClick: false, //標記符
      slider_flag: true, //隱藏標記
      flag: false, //user標記
      currentType: 1, //目前題型
      radio: [], //userResponse
      title: "請作答",
      index: 0, //全局index
      userInfo: { 
        name: null,
        id: null
      },
      topicCount: [],//題目總題數
      score: [],  //總分
      examData: { //考試基本資料
        // source: null,
        // totalScore: null,
      },
      topic: {  //考卷資訊

      },
      showQuestion: [], //目前題目
      showAnswer: {}, //目前題目的答案
      number: 1, //題號
      part: null, //
      fillAnswer: [[]], //填空題答案
      judgeAnswer: [], //是非題
      topic1Answer: [],  //作答編號,
      rightAnswer: ''
    }
  },
  created() {
    this.getCookies()
    this.getExamData()
    this.showTime()
  },
  methods: {
    getTime(date) { 
      let year = date.getFullYear()
      let month= date.getMonth()+ 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
      let day=date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
      let hours=date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
      let minutes=date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
      let seconds=date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
      return year+"-"+month+"-"+day+" "+hours+":"+minutes+":"+seconds;
    },
    getCookies() {  //獲取cookie
      this.userInfo.name = this.$cookies.get("cname")
      this.userInfo.id = this.$cookies.get("cid")
    },
    calcuScore() { //計算答題分數
      
    },
    getExamData() {
      let date = new Date()
      this.startTime = this.getTime(date)
      let examCode = this.$route.query.examCode
      this.$axios(`/api/exam/${examCode}`).then(res => {  
        this.examData = { ...res.data.data} 
        this.index = 0
        this.time = this.examData.totalScore 
        let paperId = this.examData.paperId
        this.$axios(`/api/paper/${paperId}`).then(res => {  
          this.topic = {...res.data}
          let reduceAnswer = this.topic[1][this.index]
          this.reduceAnswer = reduceAnswer
          let keys = Object.keys(this.topic) 
          keys.forEach(e => {
            let data = this.topic[e]
            this.topicCount.push(data.length)
            let currentScore = 0
            for(let i = 0; i< data.length; i++) { 
              currentScore += data[i].score
            }
            this.score.push(currentScore) 
          })
          let len = this.topicCount[1]
          let father = []
          for(let i = 0; i < len; i++) { 
            let children = [null,null,null,null]
            father.push(children)
          }
          this.fillAnswer = father
          let dataInit = this.topic[1]
          this.number = 1
          this.showQuestion = dataInit[0].question
          this.showAnswer = dataInit[0]
        })
      })
    },
    change(index) { //選擇題
      this.index = index
      let reduceAnswer = this.topic[1][this.index]
      this.reduceAnswer = reduceAnswer
      this.isFillClick = true
      this.currentType = 1
      let len = this.topic[1].length
      if(this.index < len) {
        if(this.index <= 0){
          this.index = 0
        }
        console.log(`總長度${len}`)
        console.log(`目前index:${index}`)
        this.title = "請作答"
        let Data = this.topic[1]
        // console.log(Data)
        this.showQuestion = Data[this.index].question 
        this.showAnswer = Data[this.index]
        this.number = this.index + 1
      }else if(this.index >= len) {
        this.index = 0
        this.fill(this.index)
      }
    },
    fillBG() {
      if(this.fillAnswer[this.index][0] != null) {
        this.fillAnswer[this.index][3] = true
      }
    },
    fill(index) { //填空題
      let len = this.topic[2].length
      this.currentType = 2
      this.index = index
      if(index < len) {
        if(index < 0) {
          index = this.topic[1].length -1
          this.change(index)
        }else {
          console.log(`總長度${len}`)
          console.log(`目前index:${index}`)
          this.title = "請作答"
          let Data = this.topic[2]
          console.log(Data)
          this.showQuestion = Data[index].question 
          let part= this.showQuestion.split("()").length -1
          this.number = this.topicCount[0] + index + 1
        } 
      }else if(index >= len) {
        this.index = 0
        this.judge(this.index)
      }
    },
    judge(index) { //是非題
      let len = this.topic[3].length
      this.currentType = 3
      this.index = index
      if(this.index < len) {
        if(this.index < 0){
          this.index = this.topic[2].length - 1
          this.fill(this.index)
        }else {
          console.log(`總長度${len}`)
          console.log(`目前index:${this.index}`)
          this.title = "請作答"
          let Data = this.topic[3]
          console.log(Data)
          this.showQuestion = Data[index].question 
          this.number = this.topicCount[0] + this.topicCount[1] + index + 1
        }
      }else if (this.index >= len) {
        this.index = 0
        this.change(this.index)
      }
    },
    getChangeLabel(val) { 
      this.radio[this.index] = val
      if(val) {
        let data = this.topic[1]
        this.bg_flag = true
        data[this.index]["isClick"] = true
      }
      this.topic1Answer[this.index] = val 
    },
    getJudgeLabel(val) { 
      this.judgeAnswer[this.index] = val
      if(val) {
        let data = this.topic[3]
        this.bg_flag = true
        data[this.index]["isClick"] = true
      }
    },
    previous() { //上一題
      this.index --
      switch(this.currentType) {
        case 1: 
          this.change(this.index)
          break
        case 2: 
          this.fill(this.index)
          break
        case 3:
          this.judge(this.index)
          break
      }
    },
    next() { //下一題
      this.index ++
      switch(this.currentType) {
        case 1: 
          this.change(this.index)
          break
        case 2: 
          this.fill(this.index)
          break
        case 3:
          this.judge(this.index)
          break
      }
    },
    mark() { //標記
      switch(this.currentType) {
        case 1:
          this.topic[1][this.index]["isMark"] = true //選擇題
          break
        case 2:
          this.topic[2][this.index]["isMark"] = true //填空題
          break
        case 3:
          this.topic[3][this.index]["isMark"] = true //是非題
      }
    },
    commit() { //算成績
      /* 算總分 */
      let topic1Answer = this.topic1Answer
      let finalScore = 0
      topic1Answer.forEach((element,index) => { 
        let right = null
        if(element != null) {
          switch(element) {
            case 1:
              right = "A"
              break
            case 2:
              right = "B"
              break
            case 3:
              right = "C"
              break
            case 4:
              right = "D"
          }
          if(right == this.topic[1][index].rightAnswer) {
            finalScore += this.topic[1][index].score
          }
          console.log(right,this.topic[1][index].rightAnswer)
        }
        // console.log(topic1Answer)
      })
      let fillAnswer = this.fillAnswer
      fillAnswer.forEach((element,index) => { 
        element.forEach((inner) => {
          if(this.topic[2][index].answer.includes(inner)) {
            console.log("正確")
            finalScore += this.topic[2][this.index].score
          }
        })
      });
      let topic3Answer = this.judgeAnswer
      topic3Answer.forEach((element,index) => {
        let right = null
        switch(element) {
          case 1:
            right = "T"
            break
          case 2:
            right = "F"
        }
        if(right == this.topic[3][index].answer) {
            finalScore += this.topic[3][index].score 
          }
      })
      console.log(`目前總分${finalScore}`)
      if(this.time != 0) {
        this.$confirm("是否提前交卷",{
          confirmButtonText: '立即交卷',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          console.log("交卷")
          let date = new Date()
          this.endTime = this.getTime(date)
          let answerDate = this.endTime.substr(0,10)
          this.$axios({
            url: '/api/score',
            method: 'post',
            data: {
              examCode: this.examData.examCode, //考试Id
              studentId: this.userInfo.id, //學號
              subject: this.examData.source, //課程名稱
              etScore: finalScore, //成績
              answerDate: answerDate, //日期
            }
          }).then(res => {
            if(res.data.code == 200) {
              this.$router.push({path:'/studentScore',query: {
                score: finalScore, 
                startTime: this.startTime,
                endTime: this.endTime
              }})
            }  
          })
        }).catch(() => {
          console.log("繼續答題")
        })
      }
    },
    showTime() { //倒數計時
      setInterval(() => {
        this.time -= 1
        if(this.time == 10) {
          this.$message({
            showClose: true,
            type: 'error',
            message: '剩下10分鐘！'
          })
          if(this.time == 0) {
            console.log("考試時間結束。")
          }
        }
      },1000 * 60)
    }
  },
  computed:mapState(["isPractice"])
}
</script>

<style lang="scss">
.iconfont.icon-time {
  color: #2776df;
  margin: 0px 6px 0px 20px;
}
.analysis {
  margin-top: 20px;
  .right {
    color: #2776df;
    font-size: 18px;
    border: 1px solid #2776df;
    padding: 0px 6px;
    border-radius: 4px;
    margin-left: 20px;
  }
  ul li:nth-child(2) {
    margin: 20px 0px;
  }
  ul li:nth-child(3) {
    padding: 10px;
    background-color: #d3c6c9;
    border-radius: 4px;
  }
}
.analysis span:nth-child(1) {
  font-size: 18px;
}
.mark {
  position: absolute;
  width: 4px;
  height: 4px;
  content: "";
  background-color: red;
  border-radius: 50%;
  top: 0px;
  left: 22px;
}
.border {
  position: relative;
  border: 1px solid #FF90AA !important;
}
.bg {
  background-color: #5188b8 !important;
}
.fill .el-input {
  display: inline-flex;
  width: 150px;
  margin-left: 20px;
  .el-input__inner {
    border: 1px solid transparent;
    border-bottom: 1px solid #eee;
    padding-left: 20px;
  }
}
/* slider过渡效果 */
.slider-fade-enter-active {
  transition: all .3s ease;
}
.slider-fade-leave-active {
  transition: all .3s cubic-bezier(1.0, 0.5, 0.8, 1.0);
}
.slider-fade-enter, .slider-fade-leave-to {
  transform: translateX(-100px);
  opacity: 0;
}

.operation .end li:nth-child(2) {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: rgb(39, 118, 223);
  border-radius: 50%;
  width: 50px;
  height: 50px;
  color: #fff;
}
.operation .end li {
  cursor: pointer;
  margin: 0 100px;
}
.operation {
  background-color: #fff;
  border-radius: 4px;
  padding: 10px 0px;
  margin-right: 10px;
}
.operation .end {
  display: flex;
  justify-content: center;
  align-items: center;
  color: rgb(39, 118, 223);
}
.content .number {
  display: inline-flex;
  justify-content: center;
  align-items: center;
  width: 20px;
  height: 20px;
  background-color: rgb(39, 118, 223);
  border-radius: 4px;
  margin-right: 4px;
}
.content {
  padding: 0px 20px;
}
.content .topic {
  padding: 20px 0px;
  padding-top: 30px; 
}
.right .content {
  background-color: #fff;
  margin: 10px;
  margin-left: 0px;
  height: 470px;
}
.content .el-radio-group label {
  color: #000;
  margin: 5px 0px;
}
.content .el-radio-group {
  display: flex;
  flex-direction:column;
}
.right .title p {
  margin-left: 20px;
}
.flexarea {
  display: flex;
}
.flexarea .right {
  flex: 1;
}
.auto-right {
  margin-left: auto;
  color: #2776df;
  margin-right: 10px;
}
.right .title {
  margin-right: 10px;
  padding-right: 30px;
  display: flex;
  margin-top: 10px;
  background-color: #fff;
  height: 50px;
  line-height: 50px;
}
.clearfix {
  clear: both;
}
.l-bottom .final {
  cursor: pointer;
  display: inline-block;
  text-align: center;
 background-color: rgb(39, 118, 223);
  width: 240px;
  margin: 20px 0px 20px 10px;
  border-radius: 4px;
  height: 30px;
  line-height: 30px;
  color: #fff;
  margin-top: 22px;
}
#answer .left .item {
  padding: 0px;
  font-size: 16px;
}
.l-bottom {
  border-radius: 4px;
  background-color: #fff;
}
.l-bottom .item p {
  margin-bottom: 15px;
  margin-top: 10px;
  color: #000;
  margin-left: 10px;
  letter-spacing: 2px;
}
.l-bottom .item li {
  width: 15%;
  margin-left: 5px;
  margin-bottom: 10px;
}
.l-bottom .item {
  display: flex;
  flex-direction: column;
}
.l-bottom .item ul {
  width: 100%;
  margin-bottom: -8px;
  display: flex;
  justify-content: space-around;
  flex-wrap: wrap;
}
.l-bottom .item ul li a { 
  position: relative;
  justify-content: center;
  display: inline-flex;
  align-items: center;
  width: 30px;
  height: 30px;
  border-radius: 50%;
  background-color: #fff;
  border: 1px solid #eee;
  text-align: center;
  color: #000;
  font-size: 16px;
}
.left .l-top {
  display: flex;
  justify-content: space-around;
  padding: 16px 0px;
  border: 1px solid #eee;
  border-radius: 4px;
  margin-bottom: 10px;
  background-color: #fff;
}
.left {
  width: 260px;
  height: 100%;
  margin: 10px 10px 0px 10px;
}
.left .l-top li:nth-child(2) a {
  border: 1px solid #eee;
}
.left .l-top li:nth-child(3) a {
  background-color: #5188b8;
  border: none;
}
.left .l-top li:nth-child(4) a {
  position: relative;
  border: 1px solid #eee;
}
.left .l-top li:nth-child(4) a::before {
  width: 4px;
  height: 4px;
  content: " ";
  position: absolute;
  background-color: red;
  border-radius: 50%;
  top: 0px;
  left: 16px;
}
.left .l-top li {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}
.left .l-top li a {
  display: inline-block;
  padding: 10px;
  border-radius: 50%;
  background-color: #fff;
  border: 1px solid #FF90AA;
}
#answer .top {
  background-color: rgb(39, 118, 223);
}
#answer .item {
  color: #fff;
  display: flex;
  padding: 20px;
  font-size: 20px;
}
#answer .top .item li:nth-child(1) {
  margin-right: 10px;
}
#answer .top .item li:nth-child(3) {
  position: relative;
  margin-left: auto;
}
#answer {
  padding-bottom: 30px;
}
.icon20 {
  font-size: 20px;
  font-weight: bold;
}
.item .msg {
  padding: 10px 15px;
  border-radius: 4px;
  top: 47px;
  right: -30px;
  color: #6c757d;
  position: absolute;
  border: 1px solid rgba(0,0,0,.15);
  background-color: #fff;
}
.item .msg p {
  font-size: 16px;
  width: 200px;
  text-align: left;
}
</style>
