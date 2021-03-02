var exec = require('cordova/exec');

exports.connect = function (arg1, arg2, arg3, success, error) {
    exec(success, error, 'FtpTendam', 'connect', [arg1,arg2,arg3]);
};

exports.uploadFile = function (arg1, arg2, success, error) {
    exec(success, error, 'FtpTendam', 'uploadFile', [arg1,arg2]);
};

exports.findinventorydir = function (arg1, success, error) {
    exec(success, error, 'FtpTendam', 'findinventorydir', [arg1]);
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