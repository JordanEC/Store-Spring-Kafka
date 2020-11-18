import * as ActionTypes from './ActionTypes'

export const showError = (errorMessage) => ({
    type: ActionTypes.SHOW_ERROR,
    payload: errorMessage
})

export const resetMessages = () => ({
    type: ActionTypes.RESET_MESSAGES,
    payload: null
})

export const changeSuccessful = (message) => ({
    type: ActionTypes.CHANGE_SUCCESSFUL,
    payload: message
})

export const doLoading = () => ({
    type: ActionTypes.DO_LOADING,
    payload: true
})