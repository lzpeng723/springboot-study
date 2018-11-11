(function(){}(
    window.socket = {
        connect(url) {
            let ws = null
            if (!window.WebSocket) {
                window.WebSocket = window.MozWebSocket;
            }
            if (window.WebSocket) {
                ws = new WebSocket(url);
                // this.socket = new WebSocket("ws://192.168.43.138:8888/websocket");
            }
            return ws;
        }
    }
))

