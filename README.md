# MongoDB vs TiDB vs CockroachDB benchmark

## 测试环境
cpu: Intel(R) Core(TM) i7-3610QM CPU @ 2.30GHz
mem: 32GB
os: Ferora Linux 24

## 安装和启动MongoDB
wget https://fastdl.mongodb.org/linux/mongodb-linux-x86_64-3.2.11.tgz
tar zxvf mongodb-linux-x86_64-3.2.11.tgz
mkdir db
./bin/mongod --path db


## 安装和启动TiDB
### 下载压缩包
wget http://download.pingcap.org/tidb-latest-linux-amd64.tar.gz
wget http://download.pingcap.org/tidb-latest-linux-amd64.sha256

### 检查文件完整性，返回 ok 则正确
sha256sum -c tidb-latest-linux-amd64.sha256

### 解开压缩包
tar -xzf tidb-latest-linux-amd64.tar.gz
cd tidb-latest-linux-amd64

./bin/tidb-server

## 安装和启动CockroachDB
wget https://binaries.cockroachdb.com/cockroach-latest.linux-amd64.tgz
tar xfz cockroach-latest.linux-amd64.tgz
cd cockroach-latest.linux-amd64
./cockroach

## 测试A 100%写0%读
