'use strict'

const {HotModuleReplacementPlugin} = require('webpack')
const {VueLoaderPlugin} = require('vue-loader')
const HtmlWebpackPlugin = require('html-webpack-plugin')
const CopyWebpackPlugin = require('copy-webpack-plugin')
const {resolve} = require('./utils')
const WebpackShellPlugin = require('webpack-shell-plugin')

// bfs-app-entry=game/minesweeper.js bfs-app-html-index=mine-sweeper.html
const entry = process.env['bfs-app-entry'] || './src/game/minesweeper.js';
const indexHtml = process.env['bfs-app-html-index'] || 'mine-sweeper.html';
if(!entry || !indexHtml){
    console.log('entry and html index are required');
    process.exit(-1);
}

module.exports = {
    mode: 'development',
    devServer: {
        hot: true,
        watchOptions: {
            poll: true
        }
    },
    entry: [entry],
    resolve: {
        alias: {vue: 'vue/dist/vue.js'}
    },
    module: {
        rules: [
            {
                test: /\.vue$/,
                use: 'vue-loader'
            },
            {
                test: /\.styl(us)?$/,
                use: [
                  'vue-style-loader',
                  'css-loader',
                  'stylus-loader'
                ]
            },
            {
                test: /\.js$/,
                use: 'babel-loader'
            }
        ]
    },
    plugins: [
        new HotModuleReplacementPlugin(),
        new VueLoaderPlugin(),
        new HtmlWebpackPlugin({
            filename: indexHtml,
            template: indexHtml,
            inject: true
        }),
        new CopyWebpackPlugin([
            {
                from: resolve('static/img'),
                to: resolve('dist/static/img'),
                toType: 'dir'
            },
            {
                from: resolve('static/3rd-js'),
                to: resolve('dist/static/3rd-js'),
                toType: 'dir'
            },
            {
                from: resolve('static/3rd-css'),
                to: resolve('dist/static/3rd-css'),
                toType: 'dir'
            },
            {
                from: resolve('static/fonts'),
                to: resolve('dist/static/fonts'),
                toType: 'dir'
            }
        ]),
        new WebpackShellPlugin({
            onBuildEnd: 'node ./build/copy-to-pro.js'
        })
    ]
}