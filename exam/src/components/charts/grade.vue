<!--成績統計-->
<template>
  <div id="grade">
    <div ref="box" class="box"></div>
    <div class="notFound" v-if="isNull">
      <i class="iconfont icon-LC_icon_tips_fill"></i><span>該學生未參與考試</span>
    </div>
  </div>
</template>

<script>
export default {
  name: "grade",
  data() {
    return {
      isNull: false, //原始數據
      tableDataX: [], //x軸-次數
      tableDataY: [], //y軸-分數
    }
  },
  mounted() {
    this.score();
  },
  methods: {
    score() {
      let studentId = this.$route.query.studentId
      this.$axios(`/api/score/${studentId}`).then(res => { //根據學生id查成績
        console.log(res)
        if(res.data.code == 200) {
          let rootData = res.data.data
          rootData.forEach((element,index) => {
            this.tableDataX.push(`第${index + 1}次`)
            this.tableDataY.push(element.etScore)
          });
          let boxDom = this.$refs["box"];
          let scoreCharts = this.$echarts.init(boxDom);
          let option = {
            xAxis: {
              type: "category",
              data: this.tableDataX
            },
            yAxis: {
              type: "value"
            },
            series: [
              {
                data: this.tableDataY,
                type: "line",
                itemStyle: { normal: { label: { show: true } } }
              }
            ]
          };
          scoreCharts.setOption(option);
          scoreCharts.on("mouseover", params => {
            console.log(params.value);
          });
        }else {
          this.isNull = true
        }
      })
    }
  }
};
</script>

<style lang="scss" scoped>
#grade {
  position: relative;
  .box{
    width: 600px;
    height: 400px;
  }
  .notFound {
    position: absolute;
    top: 0px;
    left: 0px;
  }
}
</style>
