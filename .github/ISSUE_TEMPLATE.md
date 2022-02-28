---
title: Test failed, {{ env.actor }}, {{ env.error }} 
labels: bug, fixCode, TestFailed
---
Hey {{ env.actor }} 👋 This commit {{ env.sha }} did not pass some tests.
Failures: 
❌ {{ env.error }}
    
