import Vue from 'vue'///dist/vue.js'
import Vuex, {mapGetters} from 'vuex'
import router from './router/router'
import Services from './services/Services'
import MessageBox from './services/MessageBox'
import '../assets/app.styl'

Vue.use(Vuex)

const level = ['success', 'info', 'warn', 'error']
let app
const store = new Vuex.Store({
    state: {
      logonUser: 'anonymous'
    },
    getters: {
      // countMul100(state){
      //   return state.count * 100
      // }
    },
    mutations: {
        updateLogonUser(state, userId){
          console.log('update logon user in store: ' + userId)
          state.logonUser = userId
        },
        clearLogonUser(state){
          state.logonUser = 'anonymous'
        }
    },
    actions: {
        increment(context){
            context.commit('increment')
        }
    }
})

// router.beforeResolve((to, from, next) => {
//   if(to.meta.requireAuth && !store.state.isLogon){
//     MessageBox.warn('Please logon first')
//     next('/login' + to.path)
//   }else{
//     next()
//   }
// })

app = new Vue({
  el: '#app',
  router,
  store,
  data: {
    storex: 'Hello world'
  },
  computed: {
    name(){
      return this.$store.state.logonUser
    }
  },
  beforeCreate(){
    console.log('app => beforcreate')
  },
  created(){
    console.log('app => created')
  },
  mounted(el){
    console.log('app => mounted')
  },
  methods: {
    logout(){
      Services.logout().done(() => {
        this.$store.commit('clearLogonUser')
        MessageBox.info('You log out')
      })
    }
  }
});

Services.getLogonUserContext()
  .done(user => store.commit('updateLogonUser', user.userId))
  .fail(() => store.commit('clearLogonUser'));
