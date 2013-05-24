/*
SQLyog Enterprise - MySQL GUI v6.06
Host - 5.5.16 : Database - kasir
*********************************************************************
Server version : 5.5.16
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

create database if not exists `kasir`;

USE `kasir`;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

/*Table structure for table `barang` */

DROP TABLE IF EXISTS `barang`;

CREATE TABLE `barang` (
  `kode_barang` int(7) NOT NULL AUTO_INCREMENT,
  `nama_barang` varchar(50) DEFAULT NULL,
  `harga` int(7) DEFAULT NULL,
  `stok` int(7) DEFAULT NULL,
  PRIMARY KEY (`kode_barang`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `barang` */

insert  into `barang`(`kode_barang`,`nama_barang`,`harga`,`stok`) values (1,'Luwak White Coffe',10000,2000),(3,'Kopiko white Coffe',12000,0);

/*Table structure for table `pembelian` */

DROP TABLE IF EXISTS `pembelian`;

CREATE TABLE `pembelian` (
  `kode_pembelian` int(6) NOT NULL AUTO_INCREMENT,
  `kode_user` int(6) DEFAULT NULL,
  `tanggal` datetime DEFAULT NULL,
  PRIMARY KEY (`kode_pembelian`),
  KEY `kode_user` (`kode_user`),
  CONSTRAINT `pembelian_ibfk_1` FOREIGN KEY (`kode_user`) REFERENCES `userapp` (`kode_user`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;

/*Data for the table `pembelian` */

insert  into `pembelian`(`kode_pembelian`,`kode_user`,`tanggal`) values (4,2,'2013-04-28 06:55:19'),(5,2,'2013-04-28 07:25:24'),(6,2,'2013-04-28 07:25:51'),(7,2,'2013-04-28 07:27:12'),(8,2,'2013-04-28 07:32:53'),(9,2,'2013-04-28 07:34:24'),(10,2,'2013-04-28 07:37:09'),(11,2,'2013-04-28 07:38:24'),(12,2,'2013-04-28 07:49:45'),(13,2,'2013-04-29 07:46:54'),(14,2,'2013-04-29 07:47:31'),(15,2,'2013-04-29 19:46:46'),(16,2,'2013-04-29 19:56:38'),(17,2,'2013-04-29 19:58:55');

/*Table structure for table `pembelian_detail` */

DROP TABLE IF EXISTS `pembelian_detail`;

CREATE TABLE `pembelian_detail` (
  `kode_pembelian` int(6) DEFAULT NULL,
  `kode_barang` int(7) DEFAULT NULL,
  `harga` int(6) DEFAULT NULL,
  `jumlah` int(6) DEFAULT NULL,
  KEY `kode_pembelian` (`kode_pembelian`),
  KEY `kode_barang` (`kode_barang`),
  CONSTRAINT `pembelian_detail_ibfk_1` FOREIGN KEY (`kode_pembelian`) REFERENCES `pembelian` (`kode_pembelian`),
  CONSTRAINT `pembelian_detail_ibfk_2` FOREIGN KEY (`kode_barang`) REFERENCES `barang` (`kode_barang`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `pembelian_detail` */

insert  into `pembelian_detail`(`kode_pembelian`,`kode_barang`,`harga`,`jumlah`) values (4,1,10000,100),(5,1,10000,1000),(6,1,10000,100),(7,1,10000,100),(8,1,10000,300),(9,1,10000,100),(10,1,10000,1000),(11,1,10000,1000),(12,1,10000,100),(13,1,10000,50),(14,1,10000,70),(15,3,12000,20),(16,3,12000,20),(17,3,12000,20);

/*Table structure for table `userapp` */

DROP TABLE IF EXISTS `userapp`;

CREATE TABLE `userapp` (
  `kode_user` int(6) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) NOT NULL,
  `password` varchar(30) DEFAULT NULL,
  `nama_asli` varchar(50) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`kode_user`,`username`),
  UNIQUE KEY `kode_user` (`kode_user`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `userapp` */

insert  into `userapp`(`kode_user`,`username`,`password`,`nama_asli`,`status`) values (1,'rizal afani','rizal','Ahmad Rizal Afani','Manager'),(2,'fira','fifir','Fira Anjani','Kasir'),(3,'rusli_99','rusli','Rohman Rusli','Gudang');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
