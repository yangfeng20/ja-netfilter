# plugin-power

### A plugin for the [ja-netfilter](https://gitee.com/ja-netfilter/ja-netfilter), it is a dragon slayer for asymmetric encryption.

#### Use the `mvn clean package` command to compile and use `power-vX.X.X-jar-with-dependencies.jar` file!

## Config file: `power.conf`

```
; for replace arguments
[Args]
EQUAL,y,z->fakeY,fakeZ

; for replace result
[Result]
EQUAL,x,y,z->fakeResult
```
