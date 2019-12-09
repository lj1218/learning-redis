#!/usr/bin/env lua
--
-- Created by lj1218.
-- Date: 2019/11/29
--
local mylist = { "start", "pause", "stop", "resume" }
function parseList(k)
    for i = 1, #mylist do
        if mylist[i] == "stop" then break end
        print(mylist[i])
    end
end

parseList(mylist)
