import { IonicNativePlugin } from '@ionic-native/core';
/**
 * @name Ftp Tendam
 * @description
 * This plugin does something
 *
 * @usage
 * ```typescript
 * import { FtpTendam } from '@ionic-native/ftp-tendam';
 *
 *
 * constructor(private ftpTendam: FtpTendam) { }
 *
 * ...
 *
 *
 * this.ftpTendam.functionName('Hello', 123)
 *   .then((res: any) => console.log(res))
 *   .catch((error: any) => console.error(error));
 *
 * ```
 */
export declare class FtpTendamOriginal extends IonicNativePlugin {
    /**
     * This function connect with ftp server
     * @param arg1 {string} Hostname
     * @param arg2 {string} UserName
     * @param arg3 {string} Password
     * @return {Promise<any>} Returns a promise that resolves when something happens
     */
    connect(arg1: string, arg2: string, arg3: string): Promise<any>;
    /**
    * This function create directory with ftp server
    * @param arg1 {string} remotePath
    * @param arg2 {string} Directory Name
    * @return {Promise<any>} Returns a promise that resolves when something happens
    */
    createinventorydir(arg1: string, arg2: string): Promise<any>;
    /**
    * This function upload with ftp server
    * @param arg1 {string} remotePath
    * @param arg2 {string} Directory Name
    * @return {Promise<any>} Returns a promise that resolves when something happens
    */
    uploadinventorydir(arg1: string, arg2: string): Promise<any>;
    /**
    * This function upload with ftp server
    * @param arg1 {string} remotePath
    * @param arg2 {string} Directory Name
    * @return {Promise<any>} Returns a promise that resolves when something happens
    */
    uploadinventoryfile(arg1: string, arg2: string): Promise<any>;
    /**
  * This function rename folder with ftp server
  * @param arg1 {string} remotePath
  * @param arg2 {string} Directory Name
  * @return {Promise<any>} Returns a promise that resolves when something happens
  */
    moveinventorydir(arg1: string, arg2: string): Promise<any>;
    /**
    * This function disconnect from the ftp server
    * @return {Promise<any>} Returns a promise that resolves when something happens
    */
    disconnect(): Promise<any>;
}

export declare const FtpTendam: FtpTendamOriginal;