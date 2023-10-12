package com.phototeca.cryptocurrencybot.domain;

public enum State {

    DEFAULT {
        @Override
        public State nexStep(State state) {
            return LANGUAGE;
        }
    },
    LANGUAGE {
        @Override
        public State nexStep(State state) {
            return TARGET_NAME;
        }
    },
    TARGET_NAME {
        @Override
        public State nexStep(State state) {
            return TARGET_WORK;
        }
    },
    TARGET_WORK {
        @Override
        public State nexStep(State state) {
            return TARGET_LINK;
        }
    },
    TARGET_LINK {
        @Override
        public State nexStep(State state) {
            return TARGET_LINK_DIALOG;
        }
    },
    TARGET_LINK_DIALOG {
        @Override
        public State nexStep(State state) {
            return TARGET_LINK_DIALOG;
        }
    },
    TARGET_PROOF {
        @Override
        public State nexStep(State state) {
            return TARGET_PROOF_DIALOG;
        }
    },
    TARGET_PROOF_DIALOG {
        @Override
        public State nexStep(State state) {
            return TARGET_PROOF_DIALOG;
        }
    },
    TARGET_FINISH {
        @Override
        public State nexStep(State state) {
            return TARGET_ADD;
        }
    },
    TARGET_ADD {
        @Override
        public State nexStep(State state) {
            return TARGET_ADD;
        }
    };

    public abstract State nexStep(State state);
}
