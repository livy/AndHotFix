# AndHotFix
Android app hot fix 

修改自开源项目numa  https://github.com/jasonross/NuwaGradle

- 支持 gradle:1.5.0

- 编译脚本内置工程中，可以根据实际需求修改编译脚本

- 加载patch自动校验patch文件签名是否和主app签名一致

- 插入class中的代码只用来避免打上CLASS_ISPREVERIFIED标记，实际不会运行到，避免在hack.apk加载之前的类运行时报错
