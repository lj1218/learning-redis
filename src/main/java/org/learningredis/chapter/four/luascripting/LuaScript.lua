--
-- Created by lj1218.
-- Date: 2019/11/29
--
local data = redis.call('GET', KEYS[1])
-- Redis.call('GET', KEYS[1]) is incorrect on the book
if data == ARGV[1] then
    redis.call('SET', KEYS[1], ARGV[2])
    return 'The value tha got sent is = ' ..  ARGV[2]
else
    redis.call('SET', KEYS[1], ARGV[3])
    return 'The value tha got sent is = ' ..  ARGV[3]
end
