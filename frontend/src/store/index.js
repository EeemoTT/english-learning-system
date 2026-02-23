import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    currentPathName: '',
    learningConfig: {
      model: "",
    },
  },
  getters: {
  },
  mutations: {
    setPath (state) {
      state.currentPathName = localStorage.getItem("currentPathName")
    },
    setLearningMode(state,mode) {
      state.learningConfig.mode = mode
    }
  },
  actions: {
  },
  modules: {
  }
})
