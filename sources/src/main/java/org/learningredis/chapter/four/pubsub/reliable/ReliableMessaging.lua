---
--- Created by lj1218.
--- Date: 2019/11/29
---
local payload = loadstring("return " .. ARGV[1])() -- lua version < 5.2 (loadstring 在 lua5.2 中已被弃用)
--local payload = load("return " .. ARGV[1])() -- lua version 5.2+  see: https://blog.csdn.net/loongsking/article/details/46339875
local result = redis.call("PUBLISH", payload.publishto, payload.msg)
if result == 0 then
    redis.call('SADD', 'MSGBOX', payload.msg)
    return 'stored messages: ' .. ARGV[1]
end
return 'consumed messages: ' .. ARGV[1]
