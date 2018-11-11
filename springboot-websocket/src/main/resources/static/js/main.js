const app = new Vue({
    el: '#app',
    data() {
        return {
            ws: null,
            userIndex: -1,
            currentIndex: 0,
            username: '',
            text: '',
            friends: []
        }
    },
    computed: {
        currentFriend() {
            return this.friends[this.currentIndex]
        },
        user() {
            return this.friends[this.userIndex]
        }
    },
    methods: {
        pushFriend(nick){
            this.friends.push({
                nick: nick,
                lastMessage: '',
                lastDateTime: null,
                messages: []
            })
        },
        login() {
            if (this.username == null || this.username.trim() == '') {
                alert('请输入用户名')
                return
            }
            this.ws = socket.connect('ws://localhost:8080/socket/' + this.username)
            this.ws.onclose = this.onError
            this.ws.onerror = this.onError
            this.ws.onmessage = this.onMessage
        },
        onError(event) {
            this.userIndex = -1
            this.friends = []
        },
        onMessage(event) {
            console.log(event.data)
            if (isNaN(event.data)) {
                //不是数字
                try {
                    console.log(JSON.parse(event.data))
                    const obj = JSON.parse(event.data);
                    if (Array.isArray(obj)) {
                        obj.forEach((value, i) => {
                            this.pushFriend(value)
                        })
                        this.userIndex = this.friends.length - 1
                        this.currentIndex = this.friends.length - 1
                    } else {
                        this.friends[obj.from].messages.push(obj)
                        this.friends[obj.from].lastDateTime = new Date()
                        this.friends[obj.from].lastMessage = obj.content
                    }

                } catch (e) {
                    this.pushFriend(event.data)
                }

            } else {
                //是数字
                console.log('好友下线')
                if (event.data < this.userIndex){
                    this.userIndex--
                }
                if (event.data < this.currentIndex){
                    this.currentIndex--
                }
                this.friends.splice(event.data, 1)
            }
        },
        getLastTime(index) {
            const time = this.friends[index].lastDateTime;
            if (time == null) {
                return ''
            }
            return time.Format(" hh:mm:ss")
        },
        sendMessage() {
            if (this.text != null && this.text != '') {
                const msg = {
                    from: this.userIndex,
                    to: this.currentIndex,
                    content: this.text
                }
                this.ws.send(JSON.stringify(msg))
                this.currentFriend.messages.push(msg)
                this.currentFriend.lastDateTime = new Date()
                this.currentFriend.lastMessage = this.text
                this.text = ''
            }

        },
        clearInput() {
            this.text = ''
        },
        receiveMessage() {
            if (this.currentIndex != this.userIndex) {
                this.currentFriend.messages.push({
                    from: this.currentIndex,
                    to: this.userIndex,
                    content: '收到消息' + new Date()
                })
                this.currentFriend.lastDateTime = new Date()
                this.currentFriend.lastMessage = '收到消息' + new Date()
            } else {
                alert('不能给自己发消息')
            }
        }
    },
    //每次页面渲染完之后滚动条在最底部
    updated() {
        this.$nextTick(function () {
            const div = document.getElementById('friendMessage');
            if (div != null) {
                div.scrollTop = div.scrollHeight;
            }
        })
    }
});