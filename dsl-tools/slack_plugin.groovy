/*
BSD 3-Clause License

Copyright (c) 2017, Thomas Phillips
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

* Redistributions of source code must retain the above copyright notice, this
  list of conditions and the following disclaimer.

* Redistributions in binary form must reproduce the above copyright notice,
  this list of conditions and the following disclaimer in the documentation
  and/or other materials provided with the distribution.

* Neither the name of the copyright holder nor the names of its
  contributors may be used to endorse or promote products derived from
  this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package dsl-tools

class SlackNotifier
{
    static int NOTIFY_STARTED          = 0b10000000
    static int NOTIFY_ABORTED          = 0b01000000
    static int NOTIFY_FAILURE          = 0b00100000
    static int NOTIFY_NOT_BUILT        = 0b00010000
    static int NOTIFY_SUCCESS          = 0b00001000
    static int NOTIFY_UNSTABLE         = 0b00000100
    static int NOTIFY_BACK_TO_NORMAL   = 0b00000010
    static int NOTIFY_REPEATED_FAILURE = 0b00000001

    static int NOTIFY_ALL = NOTIFY_STARTED | NOTIFY_ABORTED | NOTIFY_FAILURE | NOTIFY_NOT_BUILT | NOTIFY_SUCCESS | NOTIFY_UNSTABLE | NOTIFY_BACK_TO_NORMAL | NOTIFY_REPEATED_FAILURE
    static int NOTIFY_ANY_FAILURE = NOTIFY_ABORTED | NOTIFY_FAILURE | NOTIFY_NOT_BUILT | NOTIFY_UNSTABLE | NOTIFY_REPEATED_FAILURE
    static int NOTIFY_BAD_FAILURE = NOTIFY_FAILURE | NOTIFY_REPEATED_FAILURE
    static int NOTIFY_ALL_SUCCESS = NOTIFY_SUCCESS | NOTIFY_BACK_TO_NORMAL

    String custom_message = ""
    String info_choice = "AUTHORS_AND_TITLES"

    void set_custom_message(String message)
    {
        this.custom_message = message
    }

    void set_info_choice(String choice)
    {
        this.info_choice = choice
    }

    void slack_notify(def job, String domain, String channel, String credentialId, int notify, boolean test_summary)
    {
        job.with {
            publishers {
                slackNotifier {
                    teamDomain(domain)
                    authToken(null)
                    room(channel)
                    authTokenCredentialId(credentialId)
                    sendAs(null)
                    if ((notify & NOTIFY_STARTED) == NOTIFY_STARTED) {
                        startNotification(true)
                    } else {
                        startNotification(false)
                    }
                    if ((notify & NOTIFY_ABORTED) == NOTIFY_ABORTED) {
                        notifyAborted(true)
                    } else {
                        notifyAborted(false)
                    }
                    if ((notify & NOTIFY_FAILURE) == NOTIFY_FAILURE) {
                        notifyFailure(true)
                    } else {
                        notifyFailure(false)
                    }
                    if ((notify & NOTIFY_NOT_BUILT) == NOTIFY_NOT_BUILT) {
                        notifyNotBuilt(true)
                    } else {
                        notifyNotBuilt(false)
                    }
                    if ((notify & NOTIFY_SUCCESS) == NOTIFY_SUCCESS) {
                        notifySuccess(true)
                    } else {
                        notifySuccess(false)
                    }
                    if ((notify & NOTIFY_UNSTABLE) == NOTIFY_UNSTABLE) {
                        notifyUnstable(true)
                    } else {
                        notifyUnstable(false)
                    }
                    if ((notify & NOTIFY_BACK_TO_NORMAL) == NOTIFY_BACK_TO_NORMAL) {
                        notifyBackToNormal(true)
                    } else {
                        notifyBackToNormal(false)
                    }
                    if ((notify & NOTIFY_REPEATED_FAILURE) == NOTIFY_REPEATED_FAILURE) {
                        notifyRepeatedFailure(true)
                    } else {
                        notifyRepeatedFailure(false)
                    }
                    commitInfoChoice(info_choice)
                    if (custom_message != "") {
                        includeCustomMessage(true)
                    }
                    customMessage(custom_message)
                }
            }
        }
    }
}
