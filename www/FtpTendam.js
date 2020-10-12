var exec = require('cordova/exec');

exports.connect = function (arg1, arg2, arg3, success, error) {
    exec(success, error, 'FtpTendam', 'connect', [arg1,arg2,arg3]);
};

exports.createinventorydir = function (arg1, arg2, success, error) {
    exec(success, error, 'FtpTendam', 'createinventorydir', [arg1,arg2]);
};

exports.disconnect = function (success, error) {
    exec(success, error, 'FtpTendam', 'disconnect', []);
};