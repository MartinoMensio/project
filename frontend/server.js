    port: process.env.PORT || 8080,
var fs = require('fs');
var path = require('path');
var liveServer = require('live-server');

liveServer.start({
    open: false,
    root: path.resolve(__dirname, 'app/'),
});