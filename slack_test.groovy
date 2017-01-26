import dsl-tools.SlackNotifier

def myjob = job("Slack_Test") {
}
slack = new SlackNotifier()
slack.slack_notify(myjob, "domain", "#test", "slack-test", slack.NOTIFY_SUCCESS | slack.NOTIFY_FAILURE, false)
