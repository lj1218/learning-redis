#!/usr/bin/env lua
--
-- Created by lj1218.
-- Date: 2019/11/29
--
function greaterThanFunction(i, j)
    if i > j then
        print(i .. " is greater than " .. j)
        return true
    else
        print(i .. " is lesser than " .. j)
        return false
    end
end

print(greaterThanFunction(4, 5))
