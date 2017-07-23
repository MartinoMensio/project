var fs = require('fs');
var path = require('path');
var liveServer = require('live-server');

liveServer.start({
    port: process.env.FRONTEND_PORT || 8080,
    open: false,
    root: path.resolve(__dirname, 'app/'),
    // HTTPS 
    https: {
        cert: fs.readFileSync(__dirname + "/../server.cert"),
        key: fs.readFileSync(__dirname + "/../server.key"),
        passphrase: "ai-project-password"
    }
});