**Gomoku game**

Used IDE: **IntelliJ IDEA** 2022.3.2 (Ultimate Edition) Build #IU-223.8617.56, built on January 26, 2023
JDK: corretto-18.0.2
build with "**gradle build**"

GUI tests are disabled on CircleCI https://app.circleci.com/pipelines/github/mikedepetris


Project board at: https://github.com/users/mikedepetris/projects/1/views/1


TDD notes:

1. Write a test, run it, and watch it fail.
2. Make the test pass and keep the previous tests passing.
3. Commit your changes to version control.
4. Repeat until there are no more failing tests to think about.

Other notes:
https://docs.github.com/en/issues/tracking-your-work-with-issues/linking-a-pull-request-to-an-issue#manually-linking-a-pull-request-to-an-issue-using-the-pull-request-sidebar

go to github issue
open in new tab
Development
create new branch
copy commands to checkout in your local repository
    git fetch origin
    git checkout <17-test-pr>
do changes
commit push
