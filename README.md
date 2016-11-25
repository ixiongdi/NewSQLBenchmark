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
[Benchmark.png](Benchmark.png)

结论：
1.MongoDB的写入性能还是远远高于NewSQL的，而且随着访问的增多，性能没有下降，反而请求少的时候没有完全发挥
2.TiDB的写入性能在前期要比CockroachDB，但是随着访问量的增多，性能下降明显，只有最初的一半，甚至低于CockroachDB
3.CockroachDB的性能虽然最差，但是非常平稳
