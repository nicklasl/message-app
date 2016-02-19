#!/bin/bash
for i in {1..25}; do
    if ! (($i % 2)); then
        curl -H "Content-Type: application/json" -X POST -d '{"recipient":"person1","text":"ABC"}' http://localhost:9000/messages
    else
        curl -H "Content-Type: application/json" -X POST -d '{"recipient":"person2","text":"DEF"}' http://localhost:9000/messages
    fi
    sleep 1
    echo ""
done

