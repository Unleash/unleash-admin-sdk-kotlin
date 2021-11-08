# Contributing to Unleash

## Getting started

Before you begin:

- Have you read the [code of conduct](CODE_OF_CONDUCT.md)?
- Check out the [existing issues](https://github.com/Unleash/unleash-admin-sdk-kotlin-admin-sdk-kotlin/issues)

### Don't see your issue? Open one

If you spot something new, [open an issue](https://github.com/Unleash/unleash-admin-sdk-kotlin/issues/new). We'll use the issue to have a conversation about the problem you want to fix.

### Ready to make a change? Fork the repo

Fork using GitHub Desktop:

- [Getting started with GitHub Desktop](https://docs.github.com/en/desktop/installing-and-configuring-github-desktop/getting-started-with-github-desktop) will guide you through setting up Desktop.
- Once Desktop is set up, you can use it to [fork the repo](https://docs.github.com/en/desktop/contributing-and-collaborating-using-github-desktop/cloning-and-forking-repositories-from-github-desktop)!

Fork using the command line:

- [Fork the repo](https://docs.github.com/en/github/getting-started-with-github/fork-a-repo#fork-an-example-repository) so that you can make your changes without affecting the original project until you're ready to merge them.

Fork with [GitHub Codespaces](https://github.com/features/codespaces):

- [Fork, edit, and preview](https://docs.github.com/en/free-pro-team@latest/github/developing-online-with-codespaces/creating-a-codespace) using [GitHub Codespaces](https://github.com/features/codespaces) without having to install and run the project locally.

### Make your update:

Make your changes to the file(s) you'd like to update. You'll need a working JVM installation on your machine to run gradle. We're running with Java 11 as our baseline.

* Make sure your addition is tested
* Run `./gradlew spotlessApply` before you commit, just so our ktlint formatter formats the code according to the expectations of this repo
* Please make sure you can successfully run `./gradlew build` before opening a PR

### Open a pull request

* Once you're done making changes and would like to propose them for review open a pull request.

### Submit your PR & get it reviewed

- Once you submit your PR, others from the Unleash community will review it with you. The first thing you're going to want to do is a self review.
- After that, we may have questions, check back on your PR to keep up with the conversation.
- Did you have an issue, like a merge conflict? Check out GitHub's [git tutorial](https://lab.github.com/githubtraining/managing-merge-conflicts) on how to resolve merge conflicts and other issues.

### Your PR is merged!

Congratulations! The whole Unleash community thanks you. :sparkles:

Once your PR is merged, you will be proudly listed as a contributor in the [contributor chart](https://github.com/Unleash/unleash-admin-sdk-kotlin/graphs/contributors).

