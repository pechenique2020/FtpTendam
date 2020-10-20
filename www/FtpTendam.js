var exec = require('cordova/exec');

exports.connect = function (arg1, arg2, arg3, success, error) {
    exec(success, error, 'FtpTendam', 'connect', [arg1,arg2,arg3]);
};

exports.uploadinventorydir = function (arg1, arg2, success, error) {
    exec(success, error, 'FtpTendam', 'uploadinventorydir', [arg1,arg2]);
};

exports.moveinventorydir = function (arg1, arg2, success, error) {
    exec(success, error, 'FtpTendam', 'moveinventorydir', [arg1,arg2]);
};

exports.disconnect = function (success, error) {
    exec(success, error, 'FtpTendam', 'disconnect', []);
};