
<template>
  <!-- <iframe :src="currentSrc" v-show="iframeState" id="show-iframe"  frameborder=0 name="showHere" scrolling=auto ></iframe> -->
<div></div>
</template>

<script>
import { mapGetters } from "vuex";
export default {
  name: 'hello',
  data () {
    return {
      iframeState:false,
      goBackState:false,
      currentSrc:undefined
    }
  },
  mounted(){
    this.showIframe();
    const oIframe = document.getElementById('show-iframe');
    const deviceWidth = document.documentElement.clientWidth;
    const deviceHeight = document.documentElement.clientHeight;
    oIframe.style.width = deviceWidth + 'px';
    oIframe.style.height = deviceHeight + 'px';
  },
  methods:{
    goBack(){
      console.log('回到主页');
      this.goBackState = false;
      this.iframeState = false;
    },
    showIframe(){
      this.currentSrc = "http://127.0.0.1:3000";
      window.open("http://127.0.0.1:3000",'_blank');
      console.log('显示iframe');
      this.goBackState = true;
      this.iframeState = true;
    }
  }
}
</script>
