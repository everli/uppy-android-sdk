package com.everli.uppy.downloadmanager

import android.content.Context
import com.nhaarman.mockitokotlin2.any
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BrowserDownloadManagerTest {

    private lateinit var downloadManager: BrowserDownloadManager

    @Mock
    lateinit var context: Context

    @Before
    fun setUp() {
        downloadManager =
            BrowserDownloadManager(context)
    }

    @Test
    @Ignore("Method parse in android.net.Uri not mocked.")
    fun startDownload() {
        val downloadUrl = "https://update.com/available"
        downloadManager.startDownload(downloadUrl)

        verify(context).startActivity(any())
    }
}
